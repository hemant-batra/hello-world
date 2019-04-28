package app.controller;

import app.dtos.CreateElementDTO;
import app.flows.Flow;
import app.jpa.dtos.ElementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class Controller {

    private final Flow flow;

    @Autowired
    public Controller(Flow flow) {
        this.flow = flow;
    }


    @GetMapping("elements")
    public List<ElementDTO> getAllElements(@RequestParam("createdOn") Timestamp createdOn) {
        return flow.getAllElements(createdOn);
    }

    @PostMapping("element")
    public void createElement(@RequestBody CreateElementDTO createElementDTO) {
        flow.createElement(createElementDTO);
    }
}
