package com.logicerror.e_learning.services.user;

import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.user.Role;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.user.RoleNotFoundException;
import com.logicerror.e_learning.exceptions.user.UserAlreadyExistsException;
import com.logicerror.e_learning.exceptions.user.UserNotFoundException;
import com.logicerror.e_learning.mappers.UserMapper;
import com.logicerror.e_learning.repositories.RoleRepository;
import com.logicerror.e_learning.repositories.UserRepository;
import com.logicerror.e_learning.requests.user.CreateUserRequest;
import com.logicerror.e_learning.requests.user.UpdateUserRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.logicerror.e_learning.constants.MessageConstants.USER_NOT_FOUND_WITH_ID;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserUpdateService userUpdateService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Override
    @PreAuthorize("@userSecurityUtils.isAdmin()")
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    @Override
    @PreAuthorize("@userSecurityUtils.canAccessUserById(#userId)")
    public User getUserById(Long userId) {
        return getExistingUser(userId);
    }

    @Override
    @PreAuthorize("@userSecurityUtils.canAccessUserByEmail(#email)")
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", email);
                    return new UserNotFoundException("User not found with email: " + email);
                });
    }

    @Override
    @PreAuthorize("@userSecurityUtils.canAccessUserByUsername(#username)")
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found with username: {}", username);
                    return new UserNotFoundException("User not found with username: " + username);
                });
    }

    @Override
    public User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByUsername(username);
    }

    @Override
    @Transactional
    public User createUser(CreateUserRequest user) {
        if(userRepository.existsByEmailOrUsername(user.getEmail(), user.getUsername())) {
            logger.error("Email or username is already in use: {} - {}", user.getEmail(), user.getUsername());
            throw new UserAlreadyExistsException("Email or username is already in use.");
        }

        User newUser = userMapper.createUserRequestToUser(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword())); // Password should be hashed in a real application

        Role role = roleRepository.findByName(user.getRole().getName())
                .orElseThrow(() -> {
                    logger.error("Role not found: {}", user.getRole().getName());
                    return new RoleNotFoundException("Role not found: " + user.getRole().getName());
                });

        newUser.setRole(role);

        User savedUser = userRepository.save(newUser);

        logger.info("User created successfully: {}", savedUser.getUsername());

        return savedUser;
    }

    @Override
    @Transactional
    @PreAuthorize("@userSecurityUtils.canAccessUserById(#userId)")
    public User updateUser(UpdateUserRequest request, Long userId) {
        User existingUser = getExistingUser(userId);

        userUpdateService.update(existingUser, request);

        return userRepository.save(existingUser);
    }


    @Override
    @Transactional
    @PreAuthorize("@userSecurityUtils.canAccessUserById(#userId)")
    public void deleteUser(Long userId) {
        User existingUser = getExistingUser(userId);
        userRepository.delete(existingUser);
    }

    private User getExistingUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", userId);
                    return new UserNotFoundException(USER_NOT_FOUND_WITH_ID + userId);
                });
    }

    @Override
    public UserDto convertToDto(User user) {
        return userMapper.userToUserDto(user);
    }
}
