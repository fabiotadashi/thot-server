package io.redspark.thot.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateUserDTO {

    private String name;
    private List<Long> jobTitleIdList;

}
