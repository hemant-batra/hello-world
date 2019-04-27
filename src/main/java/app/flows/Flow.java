package app.flows;

import app.converters.ElementConverter;
import app.converters.UserConverter;
import app.dtos.ElementDTO;
import app.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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

    public void createUser(UserDTO userDTO) {
        String id = userDTO.getUserId();
        if(nonNull(id)) {
            UserDTO existing = userConverter.findOne(userDTO.getUserId());
            if(nonNull(existing))
                throw new RuntimeException("User with this id already exists");
        }
        userDTO.setUserId(UUID.randomUUID().toString());
        userConverter.save(userDTO);
    }

    public List<ElementDTO> getElementsByUserId(String userId) {
        return elementConverter.findAllByUserId(userId);
    }

    public ElementDTO createElement(ElementDTO elementDTO) {
        String id = elementDTO.getElementId();
        if(nonNull(id)) {
            ElementDTO existing = elementConverter.findOne(elementDTO.getElementId());
            if(nonNull(existing))
                throw new RuntimeException("Element with this id already exists");
        }
        elementDTO.setElementId(UUID.randomUUID().toString());
        return elementConverter.save(elementDTO);
    }

    public void deleteElement(String elementId) {
        ElementDTO existing = elementConverter.findOne(elementId);
        if(isNull(existing))
            throw new RuntimeException("Element with this id does not exist");
        elementConverter.delete(existing);
    }

}
