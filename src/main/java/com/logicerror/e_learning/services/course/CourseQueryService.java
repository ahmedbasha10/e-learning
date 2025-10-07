package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.constants.CourseLevel;
import com.logicerror.e_learning.dto.CourseDetailsProjection;
import com.logicerror.e_learning.dto.CourseListProjection;
import com.logicerror.e_learning.entities.course.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for course query operations
 * Used by other services that need direct access to Course entities
 */
public interface CourseQueryService {
    
    // Entity access methods for other services
    CourseDetailsProjection getCourseById(Long courseId);
    Page<CourseListProjection> getAllCourses(Pageable pageable);
    Page<CourseListProjection> getCoursesByCategory(String category, Pageable pageable);
    Page<CourseListProjection> getCoursesByLevel(CourseLevel level, Pageable pageable);
    Page<Section> getCourseSections(Long courseId, Pageable pageable);
    int getEnrolledStudentsCount(Long courseId);
    
    // Utility methods for other services
    boolean courseExists(Long courseId);
    boolean courseExistsByTitle(String title);
}
