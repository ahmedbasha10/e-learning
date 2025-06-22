package com.logicerror.e_learning.services.enrollment;

import com.logicerror.e_learning.dto.UserEnrollmentDto;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.enrollment.UserEnrollment;
import com.logicerror.e_learning.entities.enrollment.UserEnrollmentsKey;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.mappers.EnrollmentMapper;
import com.logicerror.e_learning.repositories.UserEnrollmentsRepository;
import com.logicerror.e_learning.services.course.ICourseService;
import com.logicerror.e_learning.services.user.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentServiceImpl implements EnrollmentService {
    private final ICourseService courseService;
    private final IUserService userService;
    private final UserEnrollmentsRepository userEnrollmentsRepository;
    private final EnrollmentMapper enrollmentMapper;

    // Implement the methods defined in the EnrollmentService interface
    @PreAuthorize("hasRole('STUDENT')")
    @Transactional
    @Override
    public UserEnrollment enrollStudentInCourse(Long courseId) {
        log.info("Attempting to enroll user in course with ID: {}", courseId);
        User currentUser = getCurrentUser();
        doStudentCheck(currentUser);
        Course course = getCourseById(courseId);
        doUserAlreadyEnrolledCheck(currentUser, course);
        // Here you would typically handle payment processing
        UserEnrollment savedEnrollment = constructUserEnrollment(courseId, currentUser, course);
        log.info("User enrolled successfully in course: {}", course.getTitle());
        return savedEnrollment; // Placeholder return statement
    }


    private UserEnrollment constructUserEnrollment(Long courseId, User currentUser, Course course) {
        UserEnrollment userEnrollment = new UserEnrollment();
        userEnrollment.setId(UserEnrollmentsKey.builder()
                .courseId(courseId)
                .userId(currentUser.getId())
                .build());
        userEnrollment.setUser(currentUser);
        userEnrollment.setCourse(course);
        return userEnrollmentsRepository.save(userEnrollment);
    }


    private void doUserAlreadyEnrolledCheck(User currentUser, Course course) {
        if (userEnrollmentsRepository.existsByUserAndCourse(currentUser, course)) {
            throw new AccessDeniedException("You are already enrolled in this course.");
        }
    }


    private User getCurrentUser() {
        return userService.getAuthenticatedUser();
    }

    private void doStudentCheck(User currentUser) {
        if (currentUser == null || !currentUser.isStudent()) {
            throw new AccessDeniedException("You must be a student to enroll in a course.");
        }
    }

    private Course getCourseById(Long courseId) {
        return courseService.getCourseById(courseId);
    }

    // Additional methods for managing enrollments can be added here
    @Override
    public UserEnrollmentDto convertToDto(UserEnrollment userEnrollment) {
        return enrollmentMapper.userEnrollmentToDto(userEnrollment);
    }
}
