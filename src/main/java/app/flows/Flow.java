package app.flows;

import app.converters.ElementConverter;
import app.converters.UserConverter;
import app.dtos.ElementDTO;
import app.dtos.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

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

    public UserDTO getUserByIpAddress(String ipAddress) {
        List<UserDTO> users = userConverter.findAllByIpAddress(ipAddress);
        if(users.isEmpty())
            return null;
        if(users.size() > 1)
            throw new RuntimeException("Multiple users found for this ip address");
        return users.get(0);
    }

    public UserDTO createUser(UserDTO userDTO) {
        return userConverter.post(userDTO, dto -> dto.setUserId(UUID.randomUUID().toString()));
    }

    public List<ElementDTO> getElements() {
        return elementConverter.get();
    }

    public List<ElementDTO> getElementsByCreatedOnAfter(Timestamp createdOn) {
        return elementConverter.getAllByCreatedOnAfter(createdOn);
    }

    public ElementDTO createElement(ElementDTO elementDTO) {
        return elementConverter.post(elementDTO, dto -> dto.setElementId(UUID.randomUUID().toString()));
    }
}
