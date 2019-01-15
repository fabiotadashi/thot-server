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
public class LeadDTO {

    private Long id;
    private String company;
    private String description;
    private String vendorName;
    private LeadStatus status;

}
