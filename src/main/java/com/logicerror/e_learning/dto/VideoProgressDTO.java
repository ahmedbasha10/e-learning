package com.logicerror.e_learning.dto;

public class VideoProgressDTO {
    private Long id;
    private String title;
    private boolean completed;

    public VideoProgressDTO() {
    }

    public VideoProgressDTO(Long videoId, String videoTitle, boolean completed) {
        this.id = videoId;
        this.title = videoTitle;
        this.completed = completed;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
