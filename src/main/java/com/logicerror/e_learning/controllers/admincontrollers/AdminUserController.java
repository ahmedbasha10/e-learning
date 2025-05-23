package com.logicerror.e_learning.controllers.admincontrollers;

import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base-path}/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final IUserService userService;

    // Get all users
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<UserDto>>> getAllUsers(Pageable pageable){
        Page<User> userPage = userService.getAllUsers(pageable);
        Page<UserDto> userDtoPage = userPage.map(userService::convertToDto);
        return ResponseEntity
                .ok()
                .body(new ApiResponse<>("Users retrieved successfully", userDtoPage));
    }
    
}
