package app.flows;

import app.dtos.CreateElementDTO;
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

    public List<ElementDTO> getAllElements(Timestamp createdOn) {
        Stream<ElementDTO> elementStream = isNull(createdOn) ?
                elementConverter.streamAll() :
                elementConverter.findAllByCreatedOnAfter(createdOn);
        return elementStream
                .sorted(comparing(ElementDTO::getCreatedOn))
                .collect(toList());
    }

    public void createElement(CreateElementDTO createElementDTO) {
        userConverter.post(new UserDTO(), userDTO -> {
            userDTO.setIpAddress(createElementDTO.getIpAddress());
            userDTO.setUserName(createElementDTO.getUserName());
        });

        elementConverter.post(new ElementDTO(), elementDTO -> {
            elementDTO.setElementId(UUID.randomUUID().toString());
            elementDTO.setCreatedOn(new Timestamp(System.currentTimeMillis()));
            elementDTO.setIpAddress(createElementDTO.getIpAddress());
            elementDTO.setContent(createElementDTO.getContent());
        });
    }
}
