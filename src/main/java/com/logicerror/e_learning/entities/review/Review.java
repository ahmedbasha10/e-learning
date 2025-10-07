package com.logicerror.e_learning.entities.review;

import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "reviews")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Review {

    @EmbeddedId
    private ReviewKey id;

    @Column(name = "review", nullable = false)
    private String review;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;
}
