package app.controller;

import app.dtos.ElementDTO;
import app.dtos.UserDTO;
import app.flows.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    private final Flow flow;

    @Autowired
    public Controller(Flow flow) {
        this.flow = flow;
    }


    @GetMapping("user/ip-address/{ipAddress}")
    public UserDTO getUserByIpAddress(@PathVariable("ipAddress") String ipAddress) {
        return flow.getUserByIpAddress(ipAddress);
    }

    @PostMapping("user")
    public void createUser(@RequestBody UserDTO userDTO) {
        flow.createUser(userDTO);
    }

    @GetMapping("elements/{userId}")
    public List<ElementDTO> getElementsByUserId(@PathVariable("userId") String userId) {
        return flow.getElementsByUserId(userId);
    }

    @PostMapping("element")
    public ElementDTO createElement(@RequestBody ElementDTO elementDTO) {
        return flow.createElement(elementDTO);
    }

    @DeleteMapping("element/{elementId}")
    public void deleteElement(@PathVariable("elementId") String elementId) {
        flow.deleteElement(elementId);
    }

}
