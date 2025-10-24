package com.logicerror.e_learning.courses.projections;

import com.logicerror.e_learning.courses.dtos.CourseDto;
import com.logicerror.e_learning.dto.ProjectionDTOMapper;
import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.services.utils.ResourcesURLService;

public interface CourseListProjectionDTOMapper extends ProjectionDTOMapper<CourseDto> {
    Long getId();
    String getTitle();
    String getDescription();
    String getCategory();
    String getImageUrl();
    String getTeacherFirstName();
    String getTeacherLastName();
    String getLevel();
    Integer getPrice();
    Integer getDuration();

    @Override
    default CourseDto toDTO() {
        UserDto teacher = UserDto.builder()
                .firstName(getTeacherFirstName())
                .lastName(getTeacherLastName())
                .build();
        return CourseDto.builder()
                .id(getId())
                .title(getTitle())
                .description(getDescription())
                .category(getCategory())
                .imageUrl(getImageUrl())
                .teacher(teacher)
                .level(getLevel())
                .price(getPrice())
                .duration(getDuration())
                .build();
    }
}
