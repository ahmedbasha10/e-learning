package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for course command operations
 * Used by other services that need to perform course modifications
 */
public interface CourseCommandService {
    
    Course createCourse(CreateCourseRequest request, MultipartFile thumbnail);
    Course updateCourse(Long courseId, UpdateCourseRequest request);
    void deleteCourse(Long courseId);
    void updateCourseDuration(Long courseId);
}
