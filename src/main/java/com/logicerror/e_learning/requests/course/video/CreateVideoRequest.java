package com.logicerror.e_learning.requests.course.video;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateVideoRequest {

    @NotNull(message = "Title is required")
    @Size(min = 10, max = 300, message = "Title must be between 10 and 300 characters")
    private String title;

    public CreateVideoRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
