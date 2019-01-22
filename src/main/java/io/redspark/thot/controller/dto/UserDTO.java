package io.redspark.thot.controller.dto;


import io.redspark.thot.enums.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private List<JobTitleDTO> jobTitleList;
    private List<Role> roles;

}
