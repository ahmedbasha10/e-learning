package com.logicerror.e_learning.videos.services;

import com.logicerror.e_learning.courses.dtos.CourseProgressDTO;
import com.logicerror.e_learning.videos.dtos.VideoProgressDTO;
import com.logicerror.e_learning.videos.entities.Video;
import com.logicerror.e_learning.videos.entities.VideoCompletion;
import com.logicerror.e_learning.entities.enrollment.UserEnrollment;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.general.ResourceNotFoundException;
import com.logicerror.e_learning.repositories.UserEnrollmentsRepository;
import com.logicerror.e_learning.videos.exceptions.VideoNotFoundException;
import com.logicerror.e_learning.videos.repositories.VideoCompletionRepository;
import com.logicerror.e_learning.services.user.IUserService;
import com.logicerror.e_learning.videos.repositories.VideoRepository;
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
    private final VideoQueryService videoQueryService;
    private final IUserService userService;
    private final CourseProgressDomainService courseProgressDomainService;

    @Transactional
    public void markVideoAsCompleted(Long videoId, Integer watchedDuration) {
        User user = userService.getAuthenticatedUser();

        if(videoCompletionRepository.existsByUserIdAndVideoId(user.getId(), videoId)) {
            return; // Video already marked as completed
        }

        Video video = videoQueryService.getVideoById(videoId);

        VideoCompletion videoCompletion = new VideoCompletion();
        videoCompletion.setUser(user);
        videoCompletion.setVideo(video);
        videoCompletion.setWatchedDurationInSeconds(watchedDuration);

        videoCompletionRepository.save(videoCompletion);

        UserEnrollment enrollment = userEnrollmentsRepository.findByUserIdAndCourseId(user.getId(), video.fetchCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Enrollment not found for userId %s and courseId %s", user.getId(), video.fetchCourseId())));
        courseProgressDomainService.updateCourseProgress(enrollment);
    }



     public CourseProgressDTO getCourseProgress(Long courseId) {
         User user = userService.getAuthenticatedUser();
         UserEnrollment enrollment = userEnrollmentsRepository.findByUserIdAndCourseId(user.getId(), courseId)
                 .orElseThrow(() -> new ResourceNotFoundException(String.format("Enrollment not found for userId %s and courseId %s", user.getId(), courseId)));

         List<Video> videos = videoQueryService.getCourseVideos(courseId);
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
