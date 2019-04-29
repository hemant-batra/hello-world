package app.controller;

import app.flows.Flow;
import app.jpa.dtos.ElementDTO;
import app.jpa.dtos.UserDTO;
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

    @GetMapping("user")
    public UserDTO getUser(@RequestParam("ipAddress") String ipAddress) {
        return flow.getUser(ipAddress);
    }

    @PostMapping("user")
    public void createUser(@RequestBody UserDTO userDTO) {
        flow.createUser(userDTO);
    }

    @GetMapping("elements")
    public List<ElementDTO> getAllElements(@RequestParam("createdOn") Timestamp createdOn) {
        return flow.getAllElements(createdOn);
    }

    @PostMapping("element")
    public void createElement(@RequestBody ElementDTO elementDTO) {
        flow.createElement(elementDTO);
    }
}
