package com.logicerror.e_learning.requests.course;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateCourseRequest {
    private String title;
    private String description;
    private String category;
    private String level;
    private Integer duration;
    private Integer price;
}
