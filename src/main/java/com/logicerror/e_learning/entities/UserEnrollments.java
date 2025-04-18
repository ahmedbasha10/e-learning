package com.logicerror.e_learning.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "user_enrollments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEnrollments {

    @EmbeddedId
    private UserEnrollmentsKey id;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "progress", nullable = false)
    private int progress;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}
