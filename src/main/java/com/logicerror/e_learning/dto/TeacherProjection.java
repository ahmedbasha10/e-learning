package com.logicerror.e_learning.dto;

public interface TeacherProjection extends ProjectionDTOMapper<UserDto> {
    Long getId();
    String getFirstName();
    String getLastName();

    default UserDto toUserDto() {
        return UserDto.builder()
                .id(getId())
                .firstName(getFirstName())
                .lastName(getLastName())
                .build();
    }
}
