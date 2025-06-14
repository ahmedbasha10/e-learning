package com.logicerror.e_learning.requests.course.video;

public class CreateVideoRequest {
    private String title;
    private int duration; // Duration in seconds

    public CreateVideoRequest(String title, int duration) {
        this.title = title;
        this.duration = duration;
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
}
