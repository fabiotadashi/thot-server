package io.redspark.thot.controller.dto;

import io.redspark.thot.enums.LeadStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLeadDTO {

    private String company;
    private String description;
    private Long vendorId;
    private LeadStatus leadStatus;

}
