package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.dto.CourseDto;
import com.logicerror.e_learning.dto.SectionDto;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;

/**
 * Enhanced interface for course operations
 * Provides a clean contract for course-related business operations
 */
public interface CourseService {
    
    // Query operations
    Page<CourseDto> getAllCourses(Pageable pageable);
    CourseDto getCourseById(Long courseId);
    CourseDto getCourseByTitle(String title);
    Page<CourseDto> getCoursesByCategory(String category, Pageable pageable);
    Page<CourseDto> getCoursesByLevel(String level, Pageable pageable);
    Page<CourseDto> getCoursesByTeacher(Pageable pageable);
    Page<SectionDto> getCourseSections(Long courseId, Pageable pageable);

    // Command operations
    CourseDto createCourse(CreateCourseRequest request, MultipartFile thumbnail);
    CourseDto updateCourse(Long courseId, UpdateCourseRequest request);
    void deleteCourse(Long courseId) throws AccessDeniedException;
    void updateCourseDuration(Long courseId);
    
    // Utility operations
    boolean courseExists(Long courseId);
    boolean courseExistsByTitle(String title);
    int getCourseStudentsCount(Long courseId);
}
