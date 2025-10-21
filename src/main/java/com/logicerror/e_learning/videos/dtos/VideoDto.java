package com.logicerror.e_learning.videos.dtos;

import lombok.Builder;

@Builder
public class VideoDto {
    private Long id;
    private String title;
    private int duration;
    private String url;

    public VideoDto(Long id, String title, int duration, String url) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.url = url;
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
}
