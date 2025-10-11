package com.logicerror.e_learning.sections.services.operationhandlers.delete;

import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.services.video.models.VideoCompletionCleanupResult;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Data
public class SectionDeletionContext {
    private final Long sectionId;
    private final User user;
    private Section targetSection;
    private List<VideoCompletionCleanupResult> videoCompletionCleanupResults;

    public void addVideoCompletionCleanupResult(VideoCompletionCleanupResult result) {
        if (this.videoCompletionCleanupResults == null) {
            this.videoCompletionCleanupResults = new ArrayList<>();
        }
        this.videoCompletionCleanupResults.add(result);
    }
}
