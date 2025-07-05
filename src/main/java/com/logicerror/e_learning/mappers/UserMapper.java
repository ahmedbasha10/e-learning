package com.logicerror.e_learning.mappers;

import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.logicerror.e_learning.requests.user.CreateUserRequest;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {RoleMapper.class})
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "country", source = "country")
    User createUserRequestToUser(CreateUserRequest userRequest);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "role", source = "user.role")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "country", source = "user.country")
    @Mapping(target = "city", source = "user.city")
    @Mapping(target = "state", source = "user.state")
    UserDto userToUserDto(User user);
}
