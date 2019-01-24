package io.redspark.thot.controller.dto;

import io.redspark.thot.enums.LeadStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLeadDTO {

    @NotNull
    @NotEmpty(message = "company.is.empty")
    private String company;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    private LeadStatus leadStatus;

}
