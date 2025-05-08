package com.logicerror.e_learning.mappers;

import com.logicerror.e_learning.dto.CourseDto;
import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(target = "title", source = "title")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reviews", ignore = true) 
    @Mapping(target = "sections", ignore = true)
    Course createCourseRequestToCourse(CreateCourseRequest request);

    @Mapping(target = "id", source = "course.id")
    @Mapping(target = "title", source = "course.title")
    @Mapping(target = "teacher", source = "teacher")
    CourseDto courseToCourseDto(Course course, UserDto teacher);
}
