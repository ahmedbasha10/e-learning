package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.entities.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByTitle(String title);

    Page<Course> findByCategory(String category, Pageable pageable);

    Page<Course> findByLevel(String level, Pageable pageable);

    boolean existsByTitle(String title);
}
