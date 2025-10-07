package com.logicerror.e_learning.courses.dtos;

import com.logicerror.e_learning.dto.VideoProgressDTO;

import java.util.List;

public class CourseProgressDTO {
    private int progressPercentage;
    private int completedVideos;
    private int totalVideos;
    private List<VideoProgressDTO> videos;

    public CourseProgressDTO() {
    }

    public CourseProgressDTO(int progressPercentage, int completedVideos, int totalVideos, List<VideoProgressDTO> videos) {
        this.progressPercentage = progressPercentage;
        this.completedVideos = completedVideos;
        this.totalVideos = totalVideos;
        this.videos = videos;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
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

    public List<VideoProgressDTO> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoProgressDTO> videos) {
        this.videos = videos;
    }
}
