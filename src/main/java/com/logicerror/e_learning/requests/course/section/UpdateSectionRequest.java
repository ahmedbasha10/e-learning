package com.logicerror.e_learning.requests.course.section;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class UpdateSectionRequest {

    @Size(min = 10, max = 1000, message = "Title must be between 10 and 1000 characters")
    private String title;

    @Min(value = 1, message = "Order must be at least 1")
    private Integer order;


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

}
