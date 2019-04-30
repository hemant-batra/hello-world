package app.flows;

import app.dtos.ElementDTO;
import app.entities.Element;
import app.entities.User;
import app.repositories.ElementsRepository;
import app.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

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

    public List<ElementDTO> getAllElements(Timestamp createdOn) {
        List<Element> elements = isNull(createdOn) ?
                elementsRepository.findAll() :
                elementsRepository.findAllByCreatedOnAfter(createdOn);
        return elements
                .stream()
                .sorted(comparing(Element::getCreatedOn))
                .map(this::convert)
                .collect(toList());
    }

    private ElementDTO convert(Element element) {
        ElementDTO elementDTO = new ElementDTO();
        elementDTO.setCreatedOn(element.getCreatedOn());
        elementDTO.setUserName(usersRepository.findOne(element.getIpAddress()).getUserName());
        elementDTO.setContent(element.getContent());
        return elementDTO;
    }

    public void createElement(Element element) {
        element.setElementId(UUID.randomUUID().toString());
        element.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        elementsRepository.save(element);
    }

}
