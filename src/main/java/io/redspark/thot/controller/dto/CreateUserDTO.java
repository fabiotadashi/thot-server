package io.redspark.thot.controller.dto;

import io.redspark.thot.enums.Role;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CreateUserDTO {

    @NotNull
    @NotEmpty
    private String name;

    @Size(min = 1)
    private List<Long> jobTitleIdList;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z]).{5,10}$", message = "invalid.password")
    private String password;

    @Size(min = 1)
    private List<Role> roles;

}
