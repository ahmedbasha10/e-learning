package com.logicerror.e_learning.requests.course.video;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateVideoRequest {
    private String title;
    private String url;
    private Integer duration;
}
