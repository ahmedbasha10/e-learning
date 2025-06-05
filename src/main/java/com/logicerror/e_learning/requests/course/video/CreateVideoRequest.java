package com.logicerror.e_learning.requests.course.video;

public class CreateVideoRequest {
    private String title;
    private String url;
    private int duration; // Duration in seconds

    public CreateVideoRequest(String title, String url, int duration) {
        this.title = title;
        this.url = url;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
