package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.dto.CourseDto;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICourseService {
    CourseDto getCourseById(Long courseId);
    CourseDto getCourseByTitle(String title);
    Page<CourseDto> getCoursesByCategory(String category, Pageable pageable);
    Page<CourseDto> getCoursesByLevel(String level, Pageable pageable);

    CourseDto createCourse(CreateCourseRequest createCourseRequest);

    CourseDto updateCourse(Long courseId, UpdateCourseRequest courseDto);

    void deleteCourse(Long courseId);

    CourseDto convertToDto(Course course);

}
