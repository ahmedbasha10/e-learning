package com.logicerror.e_learning.requests.course.video;

public class CreateVideoRequest {
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
