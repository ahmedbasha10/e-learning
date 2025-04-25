package com.logicerror.e_learning.controllers;

import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.requests.CreateUserRequest;
import com.logicerror.e_learning.requests.UpdateUserRequest;
import com.logicerror.e_learning.services.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Get user
    @GetMapping("/username/{username}")
    @Operation(
            summary = "Get user by username",
            description = "This endpoint allows you to retrieve a user by their username.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "User retrieved successfully"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    )
            }
    )
    public ResponseEntity<ApiResponse<UserDto>> getUserByUsername(@PathVariable String username){
        System.out.println("here");
        User user = userService.getUserByUsername(username);
        UserDto userDto = userService.convertToDto(user);
        return ResponseEntity
                .ok()
                .body(new ApiResponse<>("User retrieved successfully", userDto));
    }

    @GetMapping("/user/id/{userId}")
    @Operation(
            summary = "Get user by ID",
            description = "This endpoint allows you to retrieve a user by their ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "User retrieved successfully"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    )
            }
    )
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long userId){
        User user = userService.getUserById(userId);
        UserDto userDto = userService.convertToDto(user);
        return ResponseEntity
                .ok()
                .body(new ApiResponse<>("User retrieved successfully", userDto));
    }

    @GetMapping("/user/email/{email}")
    @Operation(
            summary = "Get user by email",
            description = "This endpoint allows you to retrieve a user by their email.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "User retrieved successfully"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    )
            }
    )
    public ResponseEntity<ApiResponse<UserDto>> getUserByEmail(@PathVariable String email){
        User user = userService.getUserByEmail(email);
        UserDto userDto = userService.convertToDto(user);
        return ResponseEntity
                .ok()
                .body(new ApiResponse<>("User retrieved successfully", userDto));
    }

    // Update user
    @PatchMapping("/user/update/{userId}")
    @Operation(
            summary = "Update user",
            description = "This endpoint allows you to update a user's details.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "User updated successfully"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data"
                    )
            }
    )
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable Long userId ,@RequestBody @Valid UpdateUserRequest request){
        User updatedUser = userService.updateUser(request, userId);
        UserDto userDto = userService.convertToDto(updatedUser);
        return ResponseEntity
                .ok()
                .body(new ApiResponse<>("User updated successfully", userDto));
    }
    // Delete user

    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity
                .ok()
                .body(new ApiResponse<>("User deleted successfully", null));
    }

}
