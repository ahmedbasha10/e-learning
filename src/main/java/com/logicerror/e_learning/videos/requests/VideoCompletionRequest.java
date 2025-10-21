package com.logicerror.e_learning.videos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoCompletionRequest {
    private Long videoId;
    private int watchedDuration;
}
