package app.controller;

import app.flows.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final Flow flow;

    @Autowired
    public Controller(Flow flow) {
        this.flow = flow;
    }


}
