package io.redspark.thot.controller.dto;

import io.redspark.thot.model.Lead;
import io.redspark.thot.model.LeadStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeadDTO {

    private Long id;
    private String company;
    private String description;
    private String vendor;
    private LeadStatus status;

}
