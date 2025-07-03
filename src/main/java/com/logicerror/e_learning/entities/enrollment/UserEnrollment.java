package com.logicerror.e_learning.entities.enrollment;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.user.User;
import jakarta.annotation.PostConstruct;
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
public class UserEnrollment {

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
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    @Column(name = "progress", nullable = false)
    private int progress;

    @Column(name = "completed_videos", nullable = false)
    private int completedVideos;

    @Column(name = "total_videos", nullable = false)
    private int totalVideos;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @PrePersist
    private void onCreate() {
        this.status = EnrollmentStatus.ENROLLED;
        this.progress = 0;
        this.date = LocalDate.now();
    }

    public UserEnrollmentsKey getId() {
        return id;
    }

    public void setId(UserEnrollmentsKey id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCompletedVideos() {
        return completedVideos;
    }

    public void setCompletedVideos(int completedVideos) {
        this.completedVideos = completedVideos;
    }

    public int getTotalVideos() {
        return totalVideos;
    }

    public void setTotalVideos(int totalVideos) {
        this.totalVideos = totalVideos;
    }
}
