package com.logicerror.e_learning.courses.mappers;

import com.logicerror.e_learning.courses.dtos.CourseDto;
import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.courses.requests.CreateCourseRequest;
import com.logicerror.e_learning.mappers.SectionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SectionMapper.class})
public interface CourseMapper {
    @Mapping(target = "duration", ignore = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reviews", ignore = true) 
    @Mapping(target = "sections", ignore = true)
    Course createCourseRequestToCourse(CreateCourseRequest request);

    @Mapping(target = "id", source = "course.id")
    @Mapping(target = "title", source = "course.title")
    @Mapping(target = "imageUrl", source = "course.imageUrl")
    @Mapping(target = "sections", source = "course.sections")
    @Mapping(target = "studentsCount", source = "course.studentsCount")
    CourseDto courseToCourseDto(Course course);
}
