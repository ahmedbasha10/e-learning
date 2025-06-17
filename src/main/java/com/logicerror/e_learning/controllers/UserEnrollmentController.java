package com.logicerror.e_learning.controllers;

import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.dto.UserEnrollmentDto;
import com.logicerror.e_learning.entities.enrollment.UserEnrollment;
import com.logicerror.e_learning.services.enrollment.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base-path}/enrollments")
@RequiredArgsConstructor
public class UserEnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping("/enroll/course/{courseId}")
    public ResponseEntity<ApiResponse<UserEnrollmentDto>> enrollStudentInCourse(@PathVariable Long courseId) {
        UserEnrollment enrollment = enrollmentService.enrollStudentInCourse(courseId);
        UserEnrollmentDto enrollmentDto = enrollmentService.convertToDto(enrollment);
        return ResponseEntity.ok(new ApiResponse<>("Enrollment successful", enrollmentDto));
    }
}
