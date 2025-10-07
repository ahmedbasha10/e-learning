package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.entities.enrollment.UserEnrollment;
import com.logicerror.e_learning.entities.enrollment.UserEnrollmentsKey;
import com.logicerror.e_learning.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserEnrollmentsRepository extends JpaRepository<UserEnrollment, UserEnrollmentsKey> {
    boolean existsByUserAndCourse(User currentUser, Course course);
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
    int countStudentsByCourseId(Long courseId);

    Optional<UserEnrollment> findByUserIdAndCourseId(Long userId, Long courseId);

    void deleteByCourseId(Long courseId);

    List<UserEnrollment> findByCourseId(Long courseId);
}
