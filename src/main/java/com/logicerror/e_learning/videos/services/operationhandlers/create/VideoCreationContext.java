package com.logicerror.e_learning.videos.services.operationhandlers.create;

import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.videos.entities.Video;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.videos.requests.CreateVideoRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
public class VideoCreationContext {
    private final User user;
    private final CreateVideoRequest request;
    private final Long courseId;
    private final Long sectionId;
    private final MultipartFile videoFile;
    private Section section;
    private Video video;
}
