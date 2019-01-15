package io.redspark.thot.controller;


import io.redspark.thot.controller.dto.LeadDTO;
import io.redspark.thot.controller.dto.UserDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("greetings")
public class HelloController {

    @GetMapping
    public String hello(@RequestParam String name){
        return String.format("¡Hola %s!", name);
    }

    @GetMapping("age/{name}")
    public String getAge(@PathVariable("name") String name){
        return String.format("Idade: %d", name.length() * 3) ;
    }

    @PostMapping
    public UserDTO birthday(@RequestBody UserDTO userDTO){
        userDTO.setAge(userDTO.getAge()+1);
        userDTO.setCreationDate(LocalDateTime.now());
        return userDTO;
    }
}
