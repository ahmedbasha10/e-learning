package com.logicerror.e_learning.mappers;

import com.logicerror.e_learning.dto.CourseDto;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(target = "title", source = "title")
    Course createCourseRequestToCourse(CreateCourseRequest request);
    CourseDto courseToCourseDto(Course course);
}
