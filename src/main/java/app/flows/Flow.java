package app.flows;

import app.jpa.entities.Element;
import app.jpa.entities.User;
import app.jpa.repositories.ElementsRepository;
import app.jpa.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class Flow {

    private final UsersRepository usersRepository;
    private final ElementsRepository elementsRepository;

    @Autowired
    public Flow(UsersRepository usersRepository, ElementsRepository elementsRepository) {
        this.usersRepository = usersRepository;
        this.elementsRepository = elementsRepository;
    }

    public User getUser(String ipAddress) {
        return usersRepository.findOne(ipAddress);
    }

    public void createUser(User user) {
        usersRepository.save(user);
    }

    public List<Element> getAllElements(Timestamp createdOn) {
        Stream<Element> elementStream = isNull(createdOn) ?
                elementsRepository.streamAll() :
                elementsRepository.streamAllByCreatedOnAfter(createdOn);
        return elementStream
                .sorted(comparing(Element::getCreatedOn))
                .collect(toList());
    }

    public void createElement(Element element) {
        element.setElementId(UUID.randomUUID().toString());
        element.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        elementsRepository.save(element);
    }

}
