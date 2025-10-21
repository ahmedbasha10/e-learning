package com.logicerror.e_learning.videos.services.operationhandlers.delete;

import com.logicerror.e_learning.videos.entities.Video;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.videos.services.models.VideoCompletionCleanupResult;
import lombok.Data;

@Data
public class VideoDeletionContext {
    private final User user;
    private final Long videoId;
    private Video targetVideo;
    private VideoCompletionCleanupResult videoCompletionCleanupResult;
}
