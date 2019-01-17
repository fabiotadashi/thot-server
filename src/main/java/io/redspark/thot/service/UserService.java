package io.redspark.thot.service;

import io.redspark.thot.controller.dto.CreateUserDTO;
import io.redspark.thot.controller.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO create(CreateUserDTO createUserDTO);

    List<UserDTO> findAll();
}
