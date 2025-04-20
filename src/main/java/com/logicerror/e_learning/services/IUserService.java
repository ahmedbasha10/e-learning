package com.logicerror.e_learning.services;

import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.user.User;
import requests.CreateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    User createUser(CreateUserRequest user);
    User updateUser(User user);
    void deleteUser(Long userId);

    UserDto convertToDto(User user);
}
