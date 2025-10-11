package com.logicerror.e_learning.courses.services;

import com.logicerror.e_learning.courses.constants.CourseLevel;
import com.logicerror.e_learning.courses.dtos.CourseDto;
import com.logicerror.e_learning.sections.dtos.SectionDto;
import com.logicerror.e_learning.courses.requests.CreateCourseRequest;
import com.logicerror.e_learning.courses.requests.UpdateCourseRequest;
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
    CourseDto getCourseById(Long courseId);
    Page<CourseDto> getAllCourses(Pageable pageable);
    Page<CourseDto> getCoursesByCategory(String category, Pageable pageable);
    Page<CourseDto> getCoursesByLevel(CourseLevel level, Pageable pageable);
    Page<SectionDto> getCourseSections(Long courseId, Pageable pageable);

    // Command operations
    CourseDto createCourse(CreateCourseRequest request, MultipartFile thumbnail);
    CourseDto updateCourse(Long courseId, UpdateCourseRequest request);
    void deleteCourse(Long courseId) throws AccessDeniedException;
    void updateCourseDuration(Long courseId);
}
