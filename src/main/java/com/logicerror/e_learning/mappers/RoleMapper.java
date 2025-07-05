package com.logicerror.e_learning.mappers;

import com.logicerror.e_learning.dto.RoleDto;
import com.logicerror.e_learning.entities.user.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "name", source = "role.name")
    RoleDto roleToRoleDto(Role role);
}
