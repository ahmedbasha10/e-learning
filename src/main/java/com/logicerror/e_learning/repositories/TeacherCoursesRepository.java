package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.entities.teacher.TeacherCourses;
import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeacherCoursesRepository extends JpaRepository<TeacherCourses, TeacherCoursesKey> {
    // Custom query methods can be defined here if needed
    List<TeacherCourses> findByCourseId(Long courseId);
//    Page<TeacherCourses> findByTeacherId(Long teacherId, Pageable pageable);

    @Query("SELECT tc.course FROM TeacherCourses tc WHERE tc.id.userId = :teacherId")
    Page<Course> findTeacherCoursesByTeacherId(Long teacherId, Pageable pageable);
}
