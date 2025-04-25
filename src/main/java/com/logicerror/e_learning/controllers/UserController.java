package com.logicerror.e_learning.controllers;

import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.requests.CreateUserRequest;
import com.logicerror.e_learning.services.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "This endpoint allows you to register a new user in the system. " +
                    "You need to provide the user details in the request body.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "User registered successfully"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "409",
                            description = "Email or username already in use"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Role not found"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    public ResponseEntity<ApiResponse<UserDto>> registerUser(@RequestBody @Valid CreateUserRequest user){
        User createdUser = userService.createUser(user);
        UserDto userDto = userService.convertToDto(createdUser);
        return ResponseEntity
                .status(201)
                .body(new ApiResponse<>("User registered successfully", userDto));
    }

}
