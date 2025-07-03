package com.logicerror.e_learning.requests.course.video;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoCompletionRequest {
    private Long videoId;
    private int watchedDuration;
}
