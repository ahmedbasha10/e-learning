package com.logicerror.e_learning.requests.course.section;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateSectionRequest {
    private String title;
    private Integer order;
    private Integer duration;

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
}
