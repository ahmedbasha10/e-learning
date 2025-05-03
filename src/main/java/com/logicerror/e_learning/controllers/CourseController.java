package com.logicerror.e_learning.controllers;

import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.dto.CourseDto;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import com.logicerror.e_learning.services.course.ICourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.base-path}/courses")
@RequiredArgsConstructor
public class CourseController {
    private final ICourseService courseService;

    // get course by id
    @GetMapping("/{courseId}")
    public ResponseEntity<ApiResponse<CourseDto>> getCourseById(@PathVariable Long courseId) {
        // Fetch course by ID
        CourseDto courseDto = courseService.getCourseById(courseId);
        // Return response
        return ResponseEntity.ok(new ApiResponse<>("Course fetched successfully", courseDto));
    }

    // get course by title
    @GetMapping("/title/{title}")
    public ResponseEntity<ApiResponse<CourseDto>> getCourseByTitle(@PathVariable String title) {
        // Fetch course by title
        CourseDto courseDto = courseService.getCourseByTitle(title);
        // Return response
        return ResponseEntity.ok(new ApiResponse<>("Course fetched successfully", courseDto));
    }

    // get courses by category
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<Page<CourseDto>>> getCoursesByCategory(@PathVariable String category, Pageable pageable) {
        // Fetch courses by category
        Page<CourseDto> courseDtoPage = courseService.getCoursesByCategory(category, pageable);
        // Return response
        return ResponseEntity.ok(new ApiResponse<>("Courses fetched successfully", courseDtoPage));
    }

    // get courses by level
    @GetMapping("/level/{level}")
    public ResponseEntity<ApiResponse<Page<CourseDto>>> getCoursesByLevel(@PathVariable String level, Pageable pageable) {
        // Fetch courses by level
        Page<CourseDto> courseDtoPage = courseService.getCoursesByLevel(level, pageable);
        // Return response
        return ResponseEntity.ok(new ApiResponse<>("Courses fetched successfully", courseDtoPage));
    }

    // create course
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CourseDto>> createCourse(@RequestBody @Valid CreateCourseRequest createCourseRequest) {
        // Create course
        CourseDto createdCourse = courseService.createCourse(createCourseRequest);
        // Return response
        return ResponseEntity.status(201).body(new ApiResponse<>("Course created successfully", createdCourse));
    }

    // update course


    // delete course

}
