package com.logicerror.e_learning.requests.course;

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


    private String category;
    private String level;
    private Integer duration;
    private Integer price;
}
