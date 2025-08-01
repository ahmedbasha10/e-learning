package com.logicerror.e_learning.services.video.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class VideoCompletionCleanupResult {
    private Long courseId;
    private Set<Long> affectedUserIds;

    public static VideoCompletionCleanupResult empty() {
        return new VideoCompletionCleanupResult(null, Set.of());
    }

    public boolean hasAffectedUsers() {
        return courseId != null && !affectedUserIds.isEmpty();
    }
}
