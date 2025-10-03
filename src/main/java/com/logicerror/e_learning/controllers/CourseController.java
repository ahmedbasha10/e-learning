package com.logicerror.e_learning.controllers;

import com.logicerror.e_learning.constants.CourseLevel;
import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.dto.CourseDto;
import com.logicerror.e_learning.dto.SectionDto;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
import com.logicerror.e_learning.services.course.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("${api.base-path}/courses")
@RequiredArgsConstructor
public class CourseController {
    
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CourseDto>>> getAllCourses(Pageable pageable) {
        Page<CourseDto> courses = courseService.getAllCourses(pageable);
        return ResponseEntity.ok(new ApiResponse<>("Courses fetched successfully", courses));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<ApiResponse<CourseDto>> getCourseById(@PathVariable Long courseId) {
        CourseDto course = courseService.getCourseById(courseId);
        return ResponseEntity.ok(new ApiResponse<>("Course fetched successfully", course));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<ApiResponse<CourseDto>> getCourseByTitle(@PathVariable String title) {
        CourseDto course = courseService.getCourseByTitle(title);
        return ResponseEntity.ok(new ApiResponse<>("Course fetched successfully", course));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<Page<CourseDto>>> getCoursesByCategory(
            @PathVariable String category, 
            Pageable pageable) {

        Page<CourseDto> courses = courseService.getCoursesByCategory(category, pageable);
        return ResponseEntity.ok(new ApiResponse<>("Courses fetched successfully", courses));
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<ApiResponse<Page<CourseDto>>> getCoursesByLevel(
            @PathVariable CourseLevel level,
            Pageable pageable) {

        Page<CourseDto> courses = courseService.getCoursesByLevel(level, pageable);
        return ResponseEntity.ok(new ApiResponse<>("Courses fetched successfully", courses));
    }

    @GetMapping("/{courseId}/sections")
    public ResponseEntity<ApiResponse<Page<SectionDto>>> getCourseSections(
            @PathVariable Long courseId, 
            Pageable pageable) {

        Page<SectionDto> sections = courseService.getCourseSections(courseId, pageable);
        return ResponseEntity.ok(new ApiResponse<>("Sections fetched successfully", sections));
    }

    // Command endpoints
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CourseDto>> createCourse(
            @RequestPart("course") @Valid CreateCourseRequest createCourseRequest,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) {

        CourseDto course = courseService.createCourse(createCourseRequest, thumbnail);
        return ResponseEntity.status(201).body(new ApiResponse<>("Course created successfully", course));
    }

    @PatchMapping("/{courseId}")
    public ResponseEntity<ApiResponse<CourseDto>> updateCourse(
            @PathVariable Long courseId, 
            @RequestBody @Valid UpdateCourseRequest updateCourseRequest) {
        
        CourseDto course = courseService.updateCourse(courseId, updateCourseRequest);
        return ResponseEntity.ok(new ApiResponse<>("Course updated successfully", course));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable Long courseId) 
            throws AccessDeniedException {

        courseService.deleteCourse(courseId);
        return ResponseEntity.ok(new ApiResponse<>("Course deleted successfully", null));
    }

    @PatchMapping("/{courseId}/duration")
    public ResponseEntity<ApiResponse<Void>> updateCourseDuration(@PathVariable Long courseId) {
        courseService.updateCourseDuration(courseId);
        return ResponseEntity.ok(new ApiResponse<>("Course duration updated successfully", null));
    }
}
