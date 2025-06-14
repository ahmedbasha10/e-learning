package com.logicerror.e_learning.entities.course;

import jakarta.persistence.*;


import java.util.List;

@Entity
@Table(name = "sections", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"course_id", "title"}),
        @UniqueConstraint(columnNames = {"course_id", "section_order"})
})
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "section_order", nullable = false)
    private Integer order;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos;

    public Section() {
    }

    public Section(String title, Integer order, Integer duration) {
        this.title = title;
        this.order = order;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
