package com.logicerror.e_learning.services.video;

import com.logicerror.e_learning.entities.course.VideoCompletion;
import com.logicerror.e_learning.entities.enrollment.EnrollmentStatus;
import com.logicerror.e_learning.entities.enrollment.UserEnrollment;
import com.logicerror.e_learning.repositories.UserEnrollmentsRepository;
import com.logicerror.e_learning.repositories.VideoCompletionRepository;
import com.logicerror.e_learning.repositories.VideoRepository;
import com.logicerror.e_learning.services.video.models.VideoCompletionCleanupResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseProgressDomainService {
    private final VideoCompletionRepository videoCompletionRepository;
    private final UserEnrollmentsRepository userEnrollmentsRepository;
    private final VideoRepository videoRepository;

    @Transactional
    public void updateCourseProgress(UserEnrollment enrollment) {
        Long userId = enrollment.getUser().getId();
        Long courseId = enrollment.getCourse().getId();
        log.debug("Updating course progress for user {} and course {}", userId, courseId);

        int totalVideos = videoRepository.countByCourseId(courseId);
        int completedVideos = videoCompletionRepository.countByUserIdAndVideo_CourseId(userId, courseId);

        int progressPercentage = totalVideos > 0 ? (completedVideos * 100) / totalVideos : 0;

        enrollment.setProgress(progressPercentage);
        enrollment.setCompletedVideos(completedVideos);
        enrollment.setTotalVideos(totalVideos);

        if (completedVideos >= totalVideos && totalVideos > 0) {
            enrollment.setStatus(EnrollmentStatus.COMPLETED);
        } else {
            enrollment.setStatus(EnrollmentStatus.IN_PROGRESS);
        }

        userEnrollmentsRepository.save(enrollment);
        log.info("Updated course progress: {}% ({}/{} videos)", progressPercentage, completedVideos, totalVideos);
    }

    /**
     * Updates course progress for all users enrolled in a course
     * Used when a video is deleted from the course
     */
    @Transactional
    public void updateCourseProgressForAllUsers(Long courseId) {
        log.debug("Updating course progress for all users in course {}", courseId);

        List<UserEnrollment> enrollments = userEnrollmentsRepository.findByCourseId(courseId);

        for (UserEnrollment enrollment : enrollments) {
            updateCourseProgress(enrollment);
        }

        log.info("Updated course progress for {} users in course {}", enrollments.size(), courseId);
    }

    /**
     * Removes video completion records for a specific video
     * Returns the course ID and list of affected user IDs for progress updates
     */
    @Transactional
    public VideoCompletionCleanupResult removeVideoCompletions(Long videoId) {
        log.debug("Removing video completion records for video {}", videoId);

        // Get all completions before deleting to know which users to update
        List<VideoCompletion> completions = videoCompletionRepository.findByVideoId(videoId);

        if (completions.isEmpty()) {
            log.debug("No completion records found for video {}", videoId);
            return VideoCompletionCleanupResult.empty();
        }

        Long courseId = completions.getFirst().getVideo().getCourse().getId();
        Set<Long> affectedUserIds = completions.stream()
                .map(completion -> completion.getUser().getId())
                .collect(Collectors.toSet());

        videoCompletionRepository.deleteByVideoId(videoId);

        log.info("Removed {} completion records for video {}", completions.size(), videoId);

        return new VideoCompletionCleanupResult(courseId, affectedUserIds);
    }
}
