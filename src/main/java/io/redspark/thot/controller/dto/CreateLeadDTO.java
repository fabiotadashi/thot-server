package io.redspark.thot.controller.dto;

import io.redspark.thot.model.enums.LeadStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLeadDTO {

    private String description;
    private LeadStatus status;
    private String company;
    private Long vendorId;

}
