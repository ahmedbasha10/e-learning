package com.logicerror.e_learning.requests.user;

import com.logicerror.e_learning.dto.RoleDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class CreateUserRequest {
    @NotNull(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]{8,20}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, one special character, and be at least 8 characters long and max 20 characters long"
    )
    private String password;

    @NotNull(message = "First name is required")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName;

    @NotNull(message = "Country name is required")
    @Size(min = 2, max = 20, message = "Country name must be between 2 and 20 characters")
    private String country;

    @NotNull(message = "City name is required")
    @Size(min = 2, max = 20, message = "City name must be between 2 and 20 characters")
    private String city;

    @NotNull(message = "State is required")
    @Size(min = 2, max = 20, message = "State name must be between 2 and 20 characters")
    private String state;

    @NotNull(message = "Role is required")
    @Valid
    private RoleDto role;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
