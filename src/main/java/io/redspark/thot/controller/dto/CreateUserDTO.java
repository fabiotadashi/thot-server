package io.redspark.thot.controller.dto;

import io.redspark.thot.enums.Role;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserDTO {

    private String name;
    private List<Long> jobTitleIdList;
    private String password;
    private List<Role> roles;

}
