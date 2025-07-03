package com.logicerror.e_learning.services.video;

import com.logicerror.e_learning.dto.CourseProgressDTO;
import com.logicerror.e_learning.dto.VideoProgressDTO;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.entities.course.VideoCompletion;
import com.logicerror.e_learning.entities.enrollment.EnrollmentStatus;
import com.logicerror.e_learning.entities.enrollment.UserEnrollment;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.general.ResourceNotFoundException;
import com.logicerror.e_learning.repositories.UserEnrollmentsRepository;
import com.logicerror.e_learning.repositories.VideoCompletionRepository;
import com.logicerror.e_learning.services.user.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoProgressService {
    private final VideoCompletionRepository videoCompletionRepository;
    private final UserEnrollmentsRepository userEnrollmentsRepository;
    private final IVideoService videoService;
    private final IUserService userService;

    @Transactional
    public void markVideoAsCompleted(Long videoId, Integer watchedDuration) {
        User user = userService.getAuthenticatedUser();

        if(videoCompletionRepository.existsByUserIdAndVideoId(user.getId(), videoId)) {
            return; // Video already marked as completed
        }
        //TODO: check if the user finished the video really not hacking
        Video video = videoService.getVideoById(videoId);

        VideoCompletion videoCompletion = new VideoCompletion();
        videoCompletion.setUser(user);
        videoCompletion.setVideo(video);
        videoCompletion.setWatchedDurationInSeconds(watchedDuration);

        videoCompletionRepository.save(videoCompletion);

        updateCourseProgress(user.getId(), video.getCourse().getId());
    }

    @Transactional
    private void updateCourseProgress(Long userId, Long courseId) {
        int totalVideos = videoService.countVideosInCourse(courseId);
        int completedVideos = videoCompletionRepository.countByUserIdAndVideo_CourseId(userId, courseId);

        int progressPercentage = totalVideos > 0 ? (completedVideos * 100) / totalVideos : 0;

        UserEnrollment enrollment = userEnrollmentsRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Enrollment not found for userId %s and courseId %s", userId, courseId)));
        enrollment.setProgress(progressPercentage);
        enrollment.setCompletedVideos(completedVideos);
        enrollment.setTotalVideos(totalVideos);
        if(completedVideos >= totalVideos && totalVideos > 0){
            enrollment.setStatus(EnrollmentStatus.COMPLETED);
        }
        userEnrollmentsRepository.save(enrollment);
    }

     public CourseProgressDTO getCourseProgress(Long courseId) {
         User user = userService.getAuthenticatedUser();
         UserEnrollment enrollment = userEnrollmentsRepository.findByUserIdAndCourseId(user.getId(), courseId)
                 .orElseThrow(() -> new ResourceNotFoundException(String.format("Enrollment not found for userId %s and courseId %s", user.getId(), courseId)));

         List<Video> videos = videoService.getCourseVideos(courseId);
         List<VideoCompletion> completedVideos = videoCompletionRepository.findByUserIdAndVideo_CourseId(user.getId(), courseId);

         Set<Long> completedVideoIds = completedVideos.stream()
                 .map(videoCompletion -> videoCompletion.getVideo().getId())
                 .collect(Collectors.toSet());

         List<VideoProgressDTO> videoProgress = videos.stream()
                 .map(video -> new VideoProgressDTO(
                         video.getId(),
                         video.getTitle(),
                         completedVideoIds.contains(video.getId())
                 ))
                 .toList();

         return new CourseProgressDTO(
                 enrollment.getProgress(),
                 enrollment.getCompletedVideos(),
                 enrollment.getTotalVideos(),
                 videoProgress
         );
     }
}
