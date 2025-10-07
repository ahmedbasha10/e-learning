package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.constants.CourseLevel;
import com.logicerror.e_learning.dto.CourseDetailsProjection;
import com.logicerror.e_learning.dto.CourseListProjection;
import com.logicerror.e_learning.entities.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("""
        SELECT
            c.id as id,
            c.title as title,
            c.description as description,
            c.category as category,
            c.level as level,
            c.imageUrl as imageUrl,
            c.duration as duration,
            c.price as price,
            u.id as teacherId,
            u.firstName as teacherFirstName,
            u.lastName as teacherLastName,
            COALESCE(COUNT(DISTINCT ue.user.id), 0) as studentsCount
        FROM Course c
        LEFT JOIN TeacherCourses tc ON tc.course.id = c.id
        LEFT JOIN User u ON u.id = tc.user.id
        LEFT JOIN UserEnrollment ue ON ue.course.id = c.id
        WHERE c.id = :courseId
        GROUP BY c.id, u.id, u.username, u.email
        """)
    Optional<CourseDetailsProjection> findCourseById(Long courseId);

    @Query("""
    SELECT c FROM Course c
    LEFT JOIN FETCH c.teachers tc
    LEFT JOIN FETCH c.sections s
    LEFT JOIN FETCH s.videos v
    WHERE c.id = :courseId
    """)
    Optional<CourseDetailsProjection> findCoursePreviewById(Long courseId);

    @Query("""
        SELECT
            c.id as id,
            c.title as title,
            c.description as description,
            c.category as category,
            c.level as level,
            c.imageUrl as imageUrl,
            c.duration as duration,
            c.price as price,
            u.id as teacherId,
            u.username as teacherUsername,
            u.email as teacherEmail,
            COALESCE(COUNT(DISTINCT ue.user.id), 0) as studentsCount
        FROM Course c
        LEFT JOIN TeacherCourses tc ON tc.course.id = c.id
        LEFT JOIN User u ON u.id = tc.user.id
        LEFT JOIN UserEnrollment ue ON ue.course.id = c.id
        WHERE c.id = :courseId
        GROUP BY c.id, u.id, u.username, u.email
        """)
    Optional<CourseDetailsProjection> findByIdWithTeacherAndStudentCount(@Param("courseId") Long courseId);

    @Query("""
        SELECT
            c.id as id,
            c.title as title,
            c.description as description,
            c.category as category,
            c.level as level,
            c.imageUrl as imageUrl,
            c.duration as duration,
            c.price as price,
            u.id as teacherId,
            u.username as teacherUsername,
            u.email as teacherEmail,
            COALESCE(COUNT(DISTINCT ue.user.id), 0) as studentsCount
        FROM Course c
        LEFT JOIN TeacherCourses tc ON tc.course.id = c.id
        LEFT JOIN User u ON u.id = tc.user.id
        LEFT JOIN UserEnrollment ue ON ue.course.id = c.id
        WHERE c.title = :title
        GROUP BY c.id, u.id, u.username, u.email
        """)
    Optional<CourseDetailsProjection> findByTitleWithTeacherAndStudentCount(@Param("title") String title);

    @Query("""
        SELECT
            c.id as id,
            c.title as title,
            c.description as description,
            c.category as category,
            c.level as level,
            c.imageUrl as imageUrl,
            c.duration as duration,
            c.price as price,
            u.id as teacherId,
            u.username as teacherUsername,
            u.email as teacherEmail,
            COALESCE(COUNT(DISTINCT ue.user.id), 0) as studentsCount
        FROM Course c
        LEFT JOIN TeacherCourses tc ON tc.course.id = c.id
        LEFT JOIN User u ON u.id = tc.user.id
        LEFT JOIN UserEnrollment ue ON ue.course.id = c.id
        GROUP BY c.id, u.id, u.username, u.email
        """)
    Page<CourseDetailsProjection> findAllWithTeacherAndStudentCount(Pageable pageable);

    @Query("""
        SELECT
            c.id as id,
            c.title as title,
            c.description as description,
            c.category as category,
            c.level as level,
            c.imageUrl as imageUrl,
            c.duration as duration,
            c.price as price,
            u.id as teacherId,
            u.firstName as teacherFirstName,
            u.lastName as teacherLastName,
            COALESCE(COUNT(DISTINCT ue.user.id), 0) as studentsCount
        FROM Course c
        LEFT JOIN TeacherCourses tc ON tc.course.id = c.id
        LEFT JOIN User u ON u.id = tc.user.id
        LEFT JOIN UserEnrollment ue ON ue.course.id = c.id
        WHERE c.title = :title
        GROUP BY c.id, u.id, u.username, u.email
        """)
    Optional<CourseDetailsProjection> findByTitle(String title);

    @Query("""
            SELECT c.id as id,
                   c.title as title,
                   c.description as description,
                   c.level as level,
                   c.category as category,
                   c.imageUrl as imageUrl,
                   c.duration as duration,
                   c.price as price,
                   tc.user.firstName as teacherFirstName,
                   tc.user.lastName as teacherLastName
                   FROM Course c INNER JOIN TeacherCourses as tc
                   ON c.id = tc.course.id""")
    Page<CourseListProjection> findAllCourses(Pageable pageable);

    @Query("""
            SELECT c.id as id,
                   c.title as title,
                   c.description as description,
                   c.level as level,
                   c.category as category,
                   c.imageUrl as imageUrl,
                   c.duration as duration,
                   c.price as price,
                   tc.user.firstName as teacherFirstName,
                   tc.user.lastName as teacherLastName
                   FROM Course c INNER JOIN TeacherCourses as tc
                   ON c.id = tc.course.id
                   WHERE c.category = :category""")
    Page<CourseListProjection> findByCategory(String category, Pageable pageable);

    @Query("""
            SELECT c.id as id,
                   c.title as title,
                   c.description as description,
                   c.level as level,
                   c.category as category,
                   c.imageUrl as imageUrl,
                   c.duration as duration,
                   c.price as price,
                   tc.user.firstName as teacherFirstName,
                   tc.user.lastName as teacherLastName
                   FROM Course c INNER JOIN TeacherCourses as tc
                   ON c.id = tc.course.id
                   WHERE c.level = :level""")
    Page<CourseListProjection> findByLevel(@Param("level") CourseLevel level, Pageable pageable);

    boolean existsByTitle(String title);


}
