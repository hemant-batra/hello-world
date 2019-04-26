package app.controller;

import app.entities.Element;
import app.repositories.ElementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    private final ElementsRepository elementsRepository;

    @Autowired
    public Controller(ElementsRepository elementsRepository) {
        this.elementsRepository = elementsRepository;
    }

    @GetMapping()
    public List<Element> getElements() {
        return elementsRepository.findAll();
    }

    @PostMapping()
    public Element postElement(@RequestBody Element element) {
        return elementsRepository.save(element);
    }

}
