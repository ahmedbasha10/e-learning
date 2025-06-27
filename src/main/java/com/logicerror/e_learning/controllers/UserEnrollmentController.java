package com.logicerror.e_learning.controllers;

import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.dto.UserEnrollmentDto;
import com.logicerror.e_learning.entities.enrollment.UserEnrollment;
import com.logicerror.e_learning.services.enrollment.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.base-path}/enrollments")
@RequiredArgsConstructor
public class UserEnrollmentController {
    private final EnrollmentService enrollmentService;

    @GetMapping("/is-enrolled/course/{courseId}")
    public ResponseEntity<ApiResponse<Boolean>> checkUserEnrollmentByCourseId(@PathVariable Long courseId) {
        boolean isEnrolled = enrollmentService.isUserEnrolledForCourse(courseId);
        return ResponseEntity.ok(new ApiResponse<>("Enrollment status retrieved successfully", isEnrolled));
    }

    @PostMapping("/enroll/course/{courseId}")
    public ResponseEntity<ApiResponse<UserEnrollmentDto>> enrollStudentInCourse(@PathVariable Long courseId) {
        UserEnrollment enrollment = enrollmentService.enrollStudentInCourse(courseId);
        UserEnrollmentDto enrollmentDto = enrollmentService.convertToDto(enrollment);
        return ResponseEntity.ok(new ApiResponse<>("Enrollment successful", enrollmentDto));
    }
}
