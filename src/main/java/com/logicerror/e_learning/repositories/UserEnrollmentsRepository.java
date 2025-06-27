package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.enrollment.UserEnrollment;
import com.logicerror.e_learning.entities.enrollment.UserEnrollmentsKey;
import com.logicerror.e_learning.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEnrollmentsRepository extends JpaRepository<UserEnrollment, UserEnrollmentsKey> {
    boolean existsByUserAndCourse(User currentUser, Course course);
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
    int countStudentsByCourseId(Long courseId);
}
