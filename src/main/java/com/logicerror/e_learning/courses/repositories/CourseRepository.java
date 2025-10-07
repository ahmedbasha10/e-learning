package com.logicerror.e_learning.courses.repositories;

import com.logicerror.e_learning.courses.constants.CourseLevel;
import com.logicerror.e_learning.courses.projections.CourseDetailsProjectionDTOMapper;
import com.logicerror.e_learning.courses.projections.CourseListProjectionDTOMapper;
import com.logicerror.e_learning.courses.projections.CoursePreviewProjectionDTOMapper;
import com.logicerror.e_learning.courses.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {


    Optional<CourseDetailsProjectionDTOMapper> findCourseById(Long courseId);


    Optional<CoursePreviewProjectionDTOMapper> findCoursePreviewById(Long courseId);

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
    Optional<CourseDetailsProjectionDTOMapper> findByIdWithTeacherAndStudentCount(@Param("courseId") Long courseId);

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
    Optional<CourseDetailsProjectionDTOMapper> findByTitleWithTeacherAndStudentCount(@Param("title") String title);

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
    Page<CourseDetailsProjectionDTOMapper> findAllWithTeacherAndStudentCount(Pageable pageable);

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
    Optional<CourseDetailsProjectionDTOMapper> findByTitle(String title);

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
    Page<CourseListProjectionDTOMapper> findAllCourses(Pageable pageable);

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
    Page<CourseListProjectionDTOMapper> findByCategory(String category, Pageable pageable);

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
    Page<CourseListProjectionDTOMapper> findByLevel(@Param("level") CourseLevel level, Pageable pageable);

    boolean existsByTitle(String title);


}
