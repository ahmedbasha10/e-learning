package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.entities.teacher.TeacherCourses;
import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherCoursesRepository extends JpaRepository<TeacherCourses, TeacherCoursesKey> {
    // Custom query methods can be defined here if needed
    List<TeacherCourses> findByCourseId(Long courseId);
}
