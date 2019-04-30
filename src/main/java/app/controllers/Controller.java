package app.controllers;

import app.dtos.ElementDTO;
import app.entities.Element;
import app.entities.User;
import app.flows.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin
public class Controller {

    private final Flow flow;

    @Autowired
    public Controller(Flow flow) {
        this.flow = flow;
    }

    @GetMapping("user")
    public User getUser(@RequestParam("ipAddress") String ipAddress) {
        return flow.getUser(ipAddress);
    }

    @PostMapping("user")
    public void createUser(@RequestBody User user) {
        flow.createUser(user);
    }

    @GetMapping("elements")
    public List<ElementDTO> getAllElements(@RequestParam(name = "createdOn", required = false) Timestamp createdOn) {
        return flow.getAllElements(createdOn);
    }

    @PostMapping("element")
    public void createElement(@RequestBody Element element) {
        flow.createElement(element);
    }
}
