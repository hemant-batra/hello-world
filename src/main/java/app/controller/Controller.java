package app.controller;

import app.dtos.ElementDTO;
import app.dtos.UserDTO;
import app.flows.Flow;
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
    public UserDTO getUserByIpAddress(@RequestParam("ipAddress") String ipAddress) {
        return flow.getUserByIpAddress(ipAddress);
    }

    @PostMapping("user")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return flow.createUser(userDTO);
    }

    @GetMapping("elements")
    public List<ElementDTO> getElements() {
        return flow.getElements();
    }

    @GetMapping("elements")
    public List<ElementDTO> getElementsByCreatedOnAfter(@RequestParam("createdOn") Timestamp createdOn) {
        return flow.getElementsByCreatedOnAfter(createdOn);
    }

    @PostMapping("element")
    public ElementDTO createElement(@RequestBody ElementDTO elementDTO) {
        return flow.createElement(elementDTO);
    }
}
