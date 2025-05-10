package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.entities.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByTitle(String title);

    Page<Course> findByCategory(String category, Pageable pageable);

    Page<Course> findByLevel(String level, Pageable pageable);

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.sections WHERE c.id = :courseId")
    Optional<Course> findByIdWithSections(@Param("courseId") Long courseId);

    boolean existsByTitle(String title);
}
