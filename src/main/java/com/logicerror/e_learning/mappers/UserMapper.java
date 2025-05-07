package com.logicerror.e_learning.mappers;

import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.logicerror.e_learning.requests.user.CreateUserRequest;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "country", source = "country")
    User createUserRequestToUser(CreateUserRequest userRequest);

    UserDto userToUserDto(User user);
}
