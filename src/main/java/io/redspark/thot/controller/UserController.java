package io.redspark.thot.controller;

import io.redspark.thot.controller.dto.CreateUserDTO;
import io.redspark.thot.controller.dto.UserDTO;
import io.redspark.thot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDTO create(@RequestBody @Valid CreateUserDTO createUserDTO) {
        return userService.create(createUserDTO);
    }

    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("me")
    public UserDTO getCurrentUser(){
        return userService.getCurrentUser();
    }

}
