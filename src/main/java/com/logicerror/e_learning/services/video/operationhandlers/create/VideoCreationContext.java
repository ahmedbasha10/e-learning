package com.logicerror.e_learning.services.video.operationhandlers.create;

import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.requests.course.video.CreateVideoRequest;
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
