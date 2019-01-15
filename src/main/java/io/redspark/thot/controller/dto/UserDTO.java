package io.redspark.thot.controller.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {

    private String name;
    private List<JobTitleDTO> jobTitleList = new ArrayList<>();

}
