package com.logicerror.e_learning.services.enrollment;

import com.logicerror.e_learning.dto.UserEnrollmentDto;
import com.logicerror.e_learning.entities.enrollment.UserEnrollment;

public interface EnrollmentService {

    boolean isUserEnrolledForCourse(Long courseId);
    UserEnrollment enrollStudentInCourse(Long courseId);
    UserEnrollmentDto convertToDto(UserEnrollment userEnrollment);

    // student enrolls in a course (userid - course id)
    // get student enrollments
    // get course students
    // cancel student enrollment from course
}
