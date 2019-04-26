package app.flows;

import app.dtos.ElementDTO;
import app.services.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Component
public class Flow {

    private final ElementService elementService;

    @Autowired
    public Flow(ElementService elementService) {
        this.elementService = elementService;
    }

    public List<ElementDTO> findAllElements() {
        return elementService.findAll();
    }

    public ElementDTO createElement(ElementDTO elementDTO) {
        String id = elementDTO.getId();
        if(nonNull(id)) {
            ElementDTO existing = elementService.findOne(elementDTO.getId());
            if(nonNull(existing))
                throw new RuntimeException("Element with this id already exists");
        }
        elementDTO.setId(UUID.randomUUID().toString());
        return elementService.save(elementDTO);
    }
}
