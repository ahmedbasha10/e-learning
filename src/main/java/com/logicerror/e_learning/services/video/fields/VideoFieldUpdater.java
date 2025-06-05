package com.logicerror.e_learning.services.video.fields;

import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.requests.course.video.UpdateVideoRequest;

public interface VideoFieldUpdater {
    void updateField(Video video, UpdateVideoRequest request);

}
