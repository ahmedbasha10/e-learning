package com.logicerror.e_learning.services;

import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.user.Role;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.RoleNotFoundException;
import com.logicerror.e_learning.exceptions.UserAlreadyExistsException;
import com.logicerror.e_learning.exceptions.UserNotFoundException;
import com.logicerror.e_learning.repositories.RoleRepository;
import com.logicerror.e_learning.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import requests.CreateUserRequest;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", userId);
                    return new UserNotFoundException("User not found with ID: " + userId);
                });
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

        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setCountry(user.getCountry());
        newUser.setCity(user.getCity());
        newUser.setState(user.getState());

        Role role = roleRepository.findByName(user.getRole().getName())
                .orElseThrow(() -> {
                    logger.error("Role not found: {}", user.getRole().getName());
                    return new RoleNotFoundException("Role not found: " + user.getRole().getName());
                });

        newUser.setRole(role);

        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
