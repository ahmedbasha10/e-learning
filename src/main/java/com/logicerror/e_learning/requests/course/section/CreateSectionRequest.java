package com.logicerror.e_learning.requests.course.section;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateSectionRequest {

    @NotNull(message = "Title is required")
    @Size(min = 10, max = 1000, message = "Title must be between 10 and 1000 characters")
    private String title;

    @NotNull(message = "Order is required")
    @Min(value = 1, message = "Order must be at least 1")
    private Integer order;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
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
