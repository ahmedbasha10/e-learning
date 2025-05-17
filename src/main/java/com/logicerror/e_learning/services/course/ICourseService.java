package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.dto.CourseDto;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICourseService {
    Course getCourseById(Long courseId);
    Course getCourseByTitle(String title);
    Page<Course> getAllCourses(Pageable pageable);
    Page<Course> getCoursesByCategory(String category, Pageable pageable);
    Page<Course> getCoursesByLevel(String level, Pageable pageable);

    Course createCourse(CreateCourseRequest createCourseRequest);

    Course updateCourse(Long courseId, UpdateCourseRequest Course);

    void deleteCourse(Long courseId);

    CourseDto convertToDto(Course course);

}
