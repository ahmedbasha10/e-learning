package com.logicerror.e_learning.services.video.operationhandlers.delete;

import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.entities.user.User;
import lombok.Data;

@Data
public class VideoDeletionContext {
    private final User user;
    private final Long videoId;
    private Video targetVideo;
}
