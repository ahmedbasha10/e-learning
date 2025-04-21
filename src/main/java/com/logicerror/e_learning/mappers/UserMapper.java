package com.logicerror.e_learning.mappers;

import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import requests.CreateUserRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    User createUserRequestToUser(CreateUserRequest userRequest);

    UserDto userToUserDto(User user);
}
