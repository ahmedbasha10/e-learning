package com.logicerror.e_learning.mappers;

import com.logicerror.e_learning.dto.UserEnrollmentDto;
import com.logicerror.e_learning.entities.enrollment.UserEnrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CourseMapper.class})
public interface EnrollmentMapper {

    @Mapping(target = "userEnrollment.id", ignore = true)
    @Mapping(target = "userDto", source = "userEnrollment.user")
    @Mapping(target = "courseDto", source = "userEnrollment.course")
    @Mapping(target = "status", source = "userEnrollment.status")
    @Mapping(target = "progress", source = "userEnrollment.progress")
    @Mapping(target = "date", source = "userEnrollment.date")
    UserEnrollmentDto userEnrollmentToDto(UserEnrollment userEnrollment);
}
