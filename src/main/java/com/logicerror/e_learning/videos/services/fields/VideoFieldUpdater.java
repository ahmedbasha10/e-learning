package com.logicerror.e_learning.videos.services.fields;

import com.logicerror.e_learning.videos.entities.Video;
import com.logicerror.e_learning.videos.requests.UpdateVideoRequest;

public interface VideoFieldUpdater {
    void updateField(Video video, UpdateVideoRequest request);

}
