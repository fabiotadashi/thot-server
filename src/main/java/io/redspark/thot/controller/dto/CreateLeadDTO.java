package io.redspark.thot.controller.dto;

import io.redspark.thot.enums.LeadStatus;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
