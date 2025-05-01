package com.logicerror.e_learning.services.user;

import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.requests.user.CreateUserRequest;
import com.logicerror.e_learning.requests.user.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    User createUser(CreateUserRequest user);
    User updateUser(UpdateUserRequest user, Long userId);
    void deleteUser(Long userId);

    UserDto convertToDto(User user);
}
