package io.redspark.thot.controller.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateUserDTO {

    private Long id;
    private String name;
    private List<Long> jobTitleIds = new ArrayList<>();

}
