package com.logicerror.e_learning.services.user;

import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.requests.user.CreateUserRequest;
import com.logicerror.e_learning.requests.user.UpdateUserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    Page<User> getAllUsers(Pageable pageable);
    User getUserById(Long userId);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    User getAuthenticatedUser();
    User createUser(CreateUserRequest user);
    User updateUser(UpdateUserRequest user, Long userId);
    void deleteUser(Long userId);

    UserDto convertToDto(User user);
}
