package app.services;

import app.dtos.ElementDTO;
import app.entities.Element;
import app.repositories.ElementsRepository;
import app.utilities.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElementService extends EntityConverter<String, Element, ElementDTO> {

    private final ElementsRepository elementsRepository;

    @Autowired
    public ElementService(ElementsRepository elementsRepository) {
        super(elementsRepository, Element.class, ElementDTO.class);
        this.elementsRepository = elementsRepository;
    }

}
