package com.logicerror.e_learning.services.video.operationhandlers.update;

import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.requests.course.video.UpdateVideoRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class VideoUpdateContext {
    private final User user;
    private final UpdateVideoRequest request;
    private final MultipartFile videoFile;
    private final Long videoId;
    private Video video;
    private String oldVideoFilePath;
}
