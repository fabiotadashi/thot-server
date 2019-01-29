package io.redspark.thot.controller.dto;

import lombok.Data;

@Data
public class ValidationErrorDTO {

    private Integer code;
    private String message;

}
