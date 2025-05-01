package com.logicerror.e_learning.services.user;

import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.user.Role;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.RoleNotFoundException;
import com.logicerror.e_learning.exceptions.UserAlreadyExistsException;
import com.logicerror.e_learning.exceptions.UserNotFoundException;
import com.logicerror.e_learning.mappers.UserMapper;
import com.logicerror.e_learning.repositories.RoleRepository;
import com.logicerror.e_learning.repositories.UserRepository;
import com.logicerror.e_learning.requests.UpdateUserRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.logicerror.e_learning.requests.CreateUserRequest;

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
    public User getUserById(Long userId) {
        return getExistingUser(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", email);
                    return new UserNotFoundException("User not found with email: " + email);
                });
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found with username: {}", username);
                    return new UserNotFoundException("User not found with username: " + username);
                });
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
    public User updateUser(UpdateUserRequest request, Long userId) {
        User existingUser = getExistingUser(userId);

        userUpdateService.update(existingUser, request);

        return userRepository.save(existingUser);
    }


    @Override
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
