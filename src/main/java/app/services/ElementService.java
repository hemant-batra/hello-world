package app.services;

import app.dtos.ElementDTO;
import app.entities.Element;
import app.repositories.ElementsRepository;
import app.utilities.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementService extends Converter<Element, ElementDTO> {

    private final ElementsRepository elementsRepository;

    @Autowired
    public ElementService(ElementsRepository elementsRepository) {
        super(Element.class, ElementDTO.class);
        this.elementsRepository = elementsRepository;
    }

    public List<ElementDTO> findAll() {
        return toDTOs(elementsRepository.findAll());
    }

    public ElementDTO findOne(String id) {
        return toDTO(elementsRepository.findOne(id));
    }

    public ElementDTO save(ElementDTO elementDTO) {
        return toDTO(elementsRepository.save(toEntity(elementDTO)));
    }
}
