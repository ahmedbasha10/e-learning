package com.logicerror.e_learning.requests.course;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateCourseRequest {
    private String title;
    private String description;
    private String category;
    private String level;
    private int duration;
    private int price;
}
