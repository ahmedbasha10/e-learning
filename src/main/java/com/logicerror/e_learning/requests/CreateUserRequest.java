package com.logicerror.e_learning.requests;

import com.logicerror.e_learning.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateUserRequest {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String state;
    private RoleDto role;
}
