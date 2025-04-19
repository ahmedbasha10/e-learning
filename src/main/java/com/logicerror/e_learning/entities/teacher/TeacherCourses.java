package com.logicerror.e_learning.entities.teacher;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "teacher_courses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherCourses {

    @EmbeddedId
    private TeacherCoursesKey id;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}
