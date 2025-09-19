package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.course.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for course query operations
 * Used by other services that need direct access to Course entities
 */
public interface CourseQueryService {
    
    // Entity access methods for other services
    Course getCourseById(Long courseId);
    Course getCourseByTitle(String title);
    Page<Course> getAllCourses(Pageable pageable);
    Page<Course> getAllCoursesWithStudentsCount(Pageable pageable);
    Page<Course> getCoursesByCategory(String category, Pageable pageable);
    Page<Course> getCoursesByLevel(String level, Pageable pageable);
    Page<Course> getCoursesByAuthenticatedTeacher(Pageable pageable);
    Page<Section> getCourseSections(Long courseId, Pageable pageable);
    int getEnrolledStudentsCount(Long courseId);
    
    // Utility methods for other services
    boolean courseExists(Long courseId);
    boolean courseExistsByTitle(String title);
}
