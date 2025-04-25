package com.logicerror.e_learning.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    @NotNull(message = "Role name is required")
    @Pattern(regexp = "(?i)^role_(student|teacher|admin)$", message = "Role name must be one of the following: role_student, role_teacher, role_admin")
    private String name;
}
