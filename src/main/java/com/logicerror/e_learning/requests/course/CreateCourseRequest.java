package com.logicerror.e_learning.requests.course;

import com.logicerror.e_learning.requests.course.section.CreateSectionRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CreateCourseRequest {
    @NotNull(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;

    @NotNull(message = "Description is required")
    @Size(min = 10, max = 10000, message = "Description must be between 10 and 10000 characters")
    private String description;

    @NotNull(message = "Category is required")
    private String category;

    @NotNull(message = "Course Level is required")
    private String level;


    @NotNull(message = "Course Duration is required")
    private Integer duration;

    @NotNull(message = "Course Price is required")
    private Integer price;

    private List<CreateSectionRequest> sections;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<CreateSectionRequest> getSections() {
        return sections;
    }

    public void setSections(List<CreateSectionRequest> sections) {
        this.sections = sections;
    }
}
