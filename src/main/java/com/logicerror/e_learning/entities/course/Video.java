package com.logicerror.e_learning.entities.course;

import jakarta.persistence.*;

@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "duration")
    private int duration;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    public Video() {
    }

    public Video(String title, int duration, String url, Course course, Section section) {
        this.title = title;
        this.duration = duration;
        this.url = url;
        this.course = course;
        this.section = section;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Long fetchSectionId() {
        if (section == null) {
            return null;
        }
        return section.getId();
    }

    public Long fetchCourseId() {
        if (course == null) {
            return null;
        }
        return course.getId();
    }
}
