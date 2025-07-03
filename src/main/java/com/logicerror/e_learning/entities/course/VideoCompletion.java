package com.logicerror.e_learning.entities.course;

import com.logicerror.e_learning.entities.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "video_completions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "video_id"}))
public class VideoCompletion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Column(name = "completedAt", nullable = false)
    private LocalDateTime completedAt;

    @Column(name = "watchedDurationSeconds", nullable = false)
    private int watchedDurationInSeconds;

    @PrePersist
    public void onCreate() {
        this.completedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public int getWatchedDurationInSeconds() {
        return watchedDurationInSeconds;
    }

    public void setWatchedDurationInSeconds(int watchedDurationInSeconds) {
        this.watchedDurationInSeconds = watchedDurationInSeconds;
    }
}
