package com.logicerror.e_learning.controllers;

import com.logicerror.e_learning.constants.ApplicationConstants;
import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.dto.LoginRequestDto;
import com.logicerror.e_learning.dto.LoginResponseDto;
import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.requests.user.CreateUserRequest;
import com.logicerror.e_learning.requests.user.UpdateUserRequest;
import com.logicerror.e_learning.services.user.IUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.base-path}/users")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final Environment env;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> registerUser(@RequestBody @Valid CreateUserRequest user){
        User createdUser = userService.createUser(user);
        UserDto userDto = userService.convertToDto(createdUser);
        return ResponseEntity
                .status(201)
                .body(new ApiResponse<>("User registered successfully", userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        String jwt = "";
        Authentication authentication = UsernamePasswordAuthenticationToken.
                unauthenticated(loginRequest.username(), loginRequest.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if(authenticationResponse != null && authenticationResponse.isAuthenticated()) {
            if(env != null) {
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                        ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                jwt = Jwts.builder().issuer("LogicError E-Learning")
                        .subject("JWT Token")
                        .claim("username", authenticationResponse.getName())
                        .claim("authorities", authenticationResponse.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date((new Date()).getTime() + 30000000))
                        .signWith(secretKey).compact();
            }
        }

        return ResponseEntity.status(HttpStatus.OK)
                .header(ApplicationConstants.JWT_HEADER, jwt)
                .body(new LoginResponseDto(HttpStatus.OK.getReasonPhrase(), jwt));
    }

    
    // Get user
    @GetMapping("/user/username/{username}")
    public ResponseEntity<ApiResponse<UserDto>> getUserByUsername(@PathVariable String username){
        System.out.println("here");
        User user = userService.getUserByUsername(username);
        UserDto userDto = userService.convertToDto(user);
        return ResponseEntity
                .ok()
                .body(new ApiResponse<>("User retrieved successfully", userDto));
    }

    @GetMapping("/user/id/{userId}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long userId){
        User user = userService.getUserById(userId);
        UserDto userDto = userService.convertToDto(user);
        return ResponseEntity
                .ok()
                .body(new ApiResponse<>("User retrieved successfully", userDto));
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<ApiResponse<UserDto>> getUserByEmail(@PathVariable String email){
        User user = userService.getUserByEmail(email);
        UserDto userDto = userService.convertToDto(user);
        return ResponseEntity
                .ok()
                .body(new ApiResponse<>("User retrieved successfully", userDto));
    }

    // Update user
    @PatchMapping("/user/update/{userId}")
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
