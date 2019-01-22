package io.redspark.thot.controller.dto;

import io.redspark.thot.enums.Role;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserDTO {

    private String name;
    private String password;
    private List<Long> jobTitleIdList;
    private List<Role> roleList;

}
