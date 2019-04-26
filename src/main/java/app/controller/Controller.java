package app.controller;

import app.Flow.Flow;
import app.dto.ElementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    private final Flow flow;

    @Autowired
    public Controller(Flow flow) {
        this.flow = flow;
    }


    @GetMapping()
    public List<ElementDTO> findAllElements() {
        return flow.findAllElements();
    }

    @PostMapping()
    public ElementDTO createElement(@RequestBody ElementDTO elementDTO) {
        return flow.createElement(elementDTO);
    }

}
