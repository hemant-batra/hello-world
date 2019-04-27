package app.converters;

import app.dtos.ElementDTO;
import app.entities.Element;
import app.repositories.ElementsRepository;
import app.utilities.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementConverter extends EntityConverter<String, Element, ElementDTO> {

    private final ElementsRepository elementsRepository;

    @Autowired
    public ElementConverter(ElementsRepository elementsRepository) {
        super(elementsRepository, Element.class, ElementDTO.class);
        this.elementsRepository = elementsRepository;
    }

    public List<ElementDTO> findAllByUserId(String userId) {
        return toDTOs(elementsRepository.findAllByUserId(userId));
    }
}
