package com.logicerror.e_learning.controllers;

import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.requests.user.CreateUserRequest;
import com.logicerror.e_learning.requests.user.UpdateUserRequest;
import com.logicerror.e_learning.services.user.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.base-path}/users")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> registerUser(@RequestBody @Valid CreateUserRequest user){
        User createdUser = userService.createUser(user);
        UserDto userDto = userService.convertToDto(createdUser);
        return ResponseEntity
                .status(201)
                .body(new ApiResponse<>("User registered successfully", userDto));
    }

    
    // Get user
    @GetMapping("/user/username/{username}")
    @PreAuthorize("@userSecurityUtils.canAccessUserByUsername(#username)")
    public ResponseEntity<ApiResponse<UserDto>> getUserByUsername(@PathVariable String username){
        System.out.println("here");
        User user = userService.getUserByUsername(username);
        UserDto userDto = userService.convertToDto(user);
        return ResponseEntity
                .ok()
                .body(new ApiResponse<>("User retrieved successfully", userDto));
    }

    @GetMapping("/user/id/{userId}")
    @PreAuthorize("@userSecurityUtils.canAccessUserById(#userId)")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long userId){
        User user = userService.getUserById(userId);
        UserDto userDto = userService.convertToDto(user);
        return ResponseEntity
                .ok()
                .body(new ApiResponse<>("User retrieved successfully", userDto));
    }

    @GetMapping("/user/email/{email}")
    @PreAuthorize("@userSecurityUtils.canAccessUserByEmail(#email)")
    public ResponseEntity<ApiResponse<UserDto>> getUserByEmail(@PathVariable String email){
        User user = userService.getUserByEmail(email);
        UserDto userDto = userService.convertToDto(user);
        return ResponseEntity
                .ok()
                .body(new ApiResponse<>("User retrieved successfully", userDto));
    }

    // Update user
    @PatchMapping("/user/update/{userId}")
    @PreAuthorize("@userSecurityUtils.canAccessUserById(#userId)")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable Long userId ,@RequestBody @Valid UpdateUserRequest request){
        User updatedUser = userService.updateUser(request, userId);
        UserDto userDto = userService.convertToDto(updatedUser);
        return ResponseEntity
                .ok()
                .body(new ApiResponse<>("User updated successfully", userDto));
    }

    // Delete user
    @DeleteMapping("/user/delete/{userId}")
    @PreAuthorize("@userSecurityUtils.canAccessUserById(#userId)")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity
                .ok()
                .body(new ApiResponse<>("User deleted successfully", null));
    }

}
