package com.logicerror.e_learning.sections.dtos;

import com.logicerror.e_learning.videos.dtos.VideoDto;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Builder
public class SectionDto {
    private Long id;
    private String title;
    private Integer order;
    private Integer duration;
    private Set<VideoDto> videos;

    public SectionDto() {
        this.videos = new HashSet<>();
    }

    public SectionDto(Long id, String title, Integer order, Integer duration, Set<VideoDto> videos) {
        this.id = id;
        this.title = title;
        this.order = order;
        this.duration = duration;
        this.videos = videos;
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Set<VideoDto> getVideos() {
        return videos;
    }

    public void setVideos(Set<VideoDto> videos) {
        this.videos = videos;
    }
}
