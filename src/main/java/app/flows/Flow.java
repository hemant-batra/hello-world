package app.flows;

import app.jpa.converters.ElementConverter;
import app.jpa.converters.UserConverter;
import app.jpa.dtos.ElementDTO;
import app.jpa.dtos.UserDTO;
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

    private final UserConverter userConverter;
    private final ElementConverter elementConverter;

    @Autowired
    public Flow(UserConverter userConverter, ElementConverter elementConverter) {
        this.userConverter = userConverter;
        this.elementConverter = elementConverter;
    }

    public UserDTO getUser(String ipAddress) {
        return userConverter.get(ipAddress);
    }

    public void createUser(UserDTO userDTO) {
        userConverter.save(userDTO);
    }

    public List<ElementDTO> getAllElements(Timestamp createdOn) {
        Stream<ElementDTO> elementStream = isNull(createdOn) ?
                elementConverter.streamAll() :
                elementConverter.findAllByCreatedOnAfter(createdOn);
        return elementStream
                .sorted(comparing(ElementDTO::getCreatedOn))
                .collect(toList());
    }

    public void createElement(ElementDTO elementDTO) {
        elementConverter.save(elementDTO, dto -> {
            dto.setElementId(UUID.randomUUID().toString());
            dto.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        });
    }

}
