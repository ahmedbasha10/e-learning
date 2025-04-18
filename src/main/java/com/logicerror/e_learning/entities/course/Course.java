package com.logicerror.e_learning.entities.course;

import com.logicerror.e_learning.entities.review.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    //TODO: maybe separate this into a separate table
    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "level", nullable = false)
    private String level;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "price", nullable = false)
    private int price;


    //TODO: Fetch lazy
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> sections;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
