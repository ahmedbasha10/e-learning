package com.logicerror.e_learning.requests.course.video;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateVideoRequest {

    @NotNull(message = "Title is required")
    @Size(min = 10, max = 300, message = "Title must be between 10 and 300 characters")
    private String title;

    @NotNull(message = "Video file identifier is required")
    private String videoFileIdentifier;

    public CreateVideoRequest(String title, String videoFileIdentifier) {
        this.title = title;
        this.videoFileIdentifier = videoFileIdentifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoFileIdentifier() {
        return videoFileIdentifier;
    }

    public void setVideoFileIdentifier(String videoFileIdentifier) {
        this.videoFileIdentifier = videoFileIdentifier;
    }
}
