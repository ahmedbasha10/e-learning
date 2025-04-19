package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.entities.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // Custom query methods can be defined here if needed
}
