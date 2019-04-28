package app.jpa.converters;

import app.jpa.dtos.ElementDTO;
import app.jpa.entities.Element;
import app.jpa.repositories.ElementsRepository;
import app.utilities.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.stream.Stream;

@Service
public class ElementConverter extends EntityConverter<String, Element, ElementDTO> {

    private final ElementsRepository elementsRepository;

    @Autowired
    public ElementConverter(ElementsRepository elementsRepository) {
        super(elementsRepository, Element.class, ElementDTO.class);
        this.elementsRepository = elementsRepository;
    }

    public Stream<ElementDTO> findAllByCreatedOnAfter(Timestamp createdOn) {
        return toDTOStream(elementsRepository.findAllByCreatedOnAfter(createdOn));
    }
}