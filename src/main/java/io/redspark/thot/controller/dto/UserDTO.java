package io.redspark.thot.controller.dto;


import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private List<JobTitleDTO> jobTitleList;

}
