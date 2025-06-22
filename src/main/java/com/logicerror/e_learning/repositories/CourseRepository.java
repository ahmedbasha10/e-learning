package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.entities.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByTitle(String title);

    Page<Course> findByCategory(String category, Pageable pageable);

    Page<Course> findByLevel(String level, Pageable pageable);

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.sections WHERE c.id = :courseId")
    Optional<Course> findByIdWithSections(@Param("courseId") Long courseId);

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.sections s WHERE c.id = :courseId AND s.id = :sectionId")
    Optional<Course> findByIdWithSection(@Param("courseId") Long courseId, @Param("sectionId") Long sectionId);

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.sections s WHERE c.id = :courseId AND s.title = :sectionTitle")
    Optional<Course> findByIdWithSectionTitle(@Param("courseId") Long courseId, @Param("sectionTitle") String sectionTitle);

    @Query("SELECT c, COUNT(ue) FROM Course c LEFT JOIN UserEnrollment ue ON ue.course.id = c.id WHERE c.id = :courseId")
    Object[] findCourseWithStudentsCount(@Param("courseId") Long courseId);

    @Query("SELECT c, COUNT(ue) FROM Course c LEFT JOIN UserEnrollment ue ON ue.course.id = c.id " +
            "WHERE c.id IN :courseIds " +
            "GROUP BY c.id")
    List<Object[]> findCoursesWithStudentsCount(List<Long> courseIds);

    boolean existsByTitle(String title);
}
