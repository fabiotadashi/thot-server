package io.redspark.thot.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeadDTO {

    private Integer id;
    private String company;
    private String description;
    private String vendor;
    private String status;

}
