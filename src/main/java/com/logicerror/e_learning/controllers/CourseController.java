package com.logicerror.e_learning.controllers;

import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.dto.CourseDto;
import com.logicerror.e_learning.dto.SectionDto;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
import com.logicerror.e_learning.services.course.ICourseService;
import com.logicerror.e_learning.services.section.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("${api.base-path}/courses")
@RequiredArgsConstructor
public class CourseController {
    private final ICourseService courseService;
    private final SectionService sectionService;

    // get all courses
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CourseDto>>> getAllCourses(Pageable pageable) {
        // Fetch all courses
        Page<Course> coursePage = courseService.getAllCourses(pageable);
        // Convert to DTO
        Page<CourseDto> courseDtoPage = coursePage.map(courseService::convertToDto);
        // Return response
        return ResponseEntity.ok(new ApiResponse<>("Courses fetched successfully", courseDtoPage));
    }

    // get course by id
    @GetMapping("/{courseId}")
    public ResponseEntity<ApiResponse<CourseDto>> getCourseById(@PathVariable Long courseId) {
        // Fetch course by ID
        Course course = courseService.getCourseById(courseId);
        // Convert to DTO
        CourseDto courseDto = courseService.convertToDto(course);
        // Return response
        return ResponseEntity.ok(new ApiResponse<>("Course fetched successfully", courseDto));
    }

    // get course by title
    @GetMapping("/title/{title}")
    public ResponseEntity<ApiResponse<CourseDto>> getCourseByTitle(@PathVariable String title) {
        // Fetch course by title
        Course course = courseService.getCourseByTitle(title);
        // Convert to DTO
        CourseDto courseDto = courseService.convertToDto(course);
        // Return response
        return ResponseEntity.ok(new ApiResponse<>("Course fetched successfully", courseDto));
    }

    // get courses by category
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<Page<CourseDto>>> getCoursesByCategory(@PathVariable String category, Pageable pageable) {
        // Fetch courses by category
        Page<Course> coursePage = courseService.getCoursesByCategory(category, pageable);
        // Convert to DTO
        Page<CourseDto> courseDtoPage = coursePage.map(courseService::convertToDto);
        // Return response
        return ResponseEntity.ok(new ApiResponse<>("Courses fetched successfully", courseDtoPage));
    }

    // get courses by level
    @GetMapping("/level/{level}")
    public ResponseEntity<ApiResponse<Page<CourseDto>>> getCoursesByLevel(@PathVariable String level, Pageable pageable) {
        // Fetch courses by level
        Page<Course> coursePage = courseService.getCoursesByLevel(level, pageable);
        // Convert to DTO
        Page<CourseDto> courseDtoPage = coursePage.map(courseService::convertToDto);
        // Return response
        return ResponseEntity.ok(new ApiResponse<>("Courses fetched successfully", courseDtoPage));
    }

    @GetMapping("/{courseId}/sections")
    public ResponseEntity<ApiResponse<Page<SectionDto>>> getCourseSections(@PathVariable Long courseId, Pageable pageable) {
        Page<Section> sectionPage = courseService.getCourseSections(courseId, pageable);
        Page<SectionDto> sectionDtoPage = sectionPage.map(sectionService::convertToDto);
        return ResponseEntity.ok(new ApiResponse<>("Sections fetched successfully", sectionDtoPage));
    }

    // create course
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CourseDto>> createCourse(@RequestBody @Valid CreateCourseRequest createCourseRequest) {
        // Create course
        Course createdCourse = courseService.createCourse(createCourseRequest);
        // Convert to DTO
        CourseDto courseDto = courseService.convertToDto(createdCourse);
        // Return response
        return ResponseEntity.status(201).body(new ApiResponse<>("Course created successfully", courseDto));
    }

    // update course
    @PatchMapping("/{courseId}")
    public ResponseEntity<ApiResponse<CourseDto>> updateCourse(@PathVariable Long courseId, @RequestBody @Valid UpdateCourseRequest updateCourseRequest) throws AccessDeniedException {
        // Update course
        Course updatedCourse = courseService.updateCourse(courseId, updateCourseRequest);
        // Convert to DTO
        CourseDto courseDto = courseService.convertToDto(updatedCourse);
        // Return response
        return ResponseEntity.ok(new ApiResponse<>("Course updated successfully", courseDto));
    }


    // delete course
    @DeleteMapping("/{courseId}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable Long courseId) throws AccessDeniedException {
        // Delete course
        courseService.deleteCourse(courseId);
        // Return response
        return ResponseEntity.ok(new ApiResponse<>("Course deleted successfully", null));
    }

}
