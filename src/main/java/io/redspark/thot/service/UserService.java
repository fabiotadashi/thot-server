package io.redspark.thot.service;

import io.redspark.thot.controller.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO create(UserDTO userDTO);

    List<UserDTO> findAll();
}
