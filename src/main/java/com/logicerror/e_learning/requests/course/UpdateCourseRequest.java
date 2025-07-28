package com.logicerror.e_learning.requests.course;

import com.logicerror.e_learning.constants.CourseLevel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateCourseRequest {
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;

    @Size(min = 10, max = 10000, message = "Description must be between 10 and 10000 characters")
    private String description;

    //TODO: create another table and strict this value
    private String category;

    // strict this value to a set of predefined levels 'beginner', 'intermediate', 'advanced'
    @Enumerated(EnumType.STRING)
    private CourseLevel level;

    @Min(value = 1, message = "Price must be at least 1")
    private Integer price;
}
