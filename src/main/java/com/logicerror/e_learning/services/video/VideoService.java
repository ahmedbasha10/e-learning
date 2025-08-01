package com.logicerror.e_learning.services.video;

import com.logicerror.e_learning.dto.VideoDto;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.video.VideoCreationFailedException;
import com.logicerror.e_learning.exceptions.video.VideoNotFoundException;
import com.logicerror.e_learning.mappers.VideoMapper;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import com.logicerror.e_learning.repositories.VideoRepository;
import com.logicerror.e_learning.requests.course.video.BatchCreateVideoRequest;
import com.logicerror.e_learning.requests.course.video.CreateVideoRequest;
import com.logicerror.e_learning.requests.course.video.UpdateVideoRequest;
import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.services.user.IUserService;
import com.logicerror.e_learning.services.video.models.VideoCreationChainBuilder;
import com.logicerror.e_learning.services.video.models.VideoDeletionChainBuilder;
import com.logicerror.e_learning.services.video.models.VideoUpdateChainBuilder;
import com.logicerror.e_learning.services.video.operationhandlers.create.VideoCreationContext;
import com.logicerror.e_learning.services.video.operationhandlers.delete.VideoDeletionContext;
import com.logicerror.e_learning.services.video.operationhandlers.update.VideoUpdateContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoService implements IVideoService {
    private final VideoRepository videoRepository;
    private final IUserService userService;
    private final TeacherCoursesRepository teacherCoursesRepository;
    private final VideoMapper videoMapper;
    private final VideoCreationChainBuilder videoCreationChainBuilder;
    private final VideoUpdateChainBuilder videoUpdateChainBuilder;
    private final VideoDeletionChainBuilder videoDeletionChainBuilder;

    @Override
    public Video getVideoById(Long id) {
        log.debug("Fetching video by ID: {}", id);
        return videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found with id: " + id));
    }

    @Override
    public Video getVideoByTitleAndCourse(String title, Long courseId) {
        log.debug("Fetching video by title: '{}' and course ID: {}", title, courseId);
        return videoRepository.findByTitleAndCourseId(title, courseId)
                .orElseThrow(() -> new VideoNotFoundException("Video not found with title: " + title + " and course ID: " + courseId));
    }

    @Override
    public Video getVideoByTitleAndSection(String title, Long sectionId) {
        log.debug("Fetching video by title: '{}' and section ID: {}", title, sectionId);
        return videoRepository.findByTitleAndSectionId(title, sectionId)
                .orElseThrow(() -> new VideoNotFoundException("Video not found with title: " + title + " and section ID: " + sectionId));
    }

    @Override
    public List<Video> getCourseVideos(Long courseId) {
        log.debug("Fetching videos for course with ID: {}", courseId);
        List<Video> videos = videoRepository.findByCourseId(courseId);
        log.info("Found {} videos for course with ID: {}", videos.size(), courseId);
        return videos;
    }

    @Override
    public int countVideosInCourse(Long courseId) {
        log.debug("Counting videos in course with ID: {}", courseId);
        int count = videoRepository.countByCourseId(courseId);
        log.info("Counted {} videos in course with ID: {}", count, courseId);
        return count;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('TEACHER')")
    public Video createVideo(CreateVideoRequest request, Long courseId, Long sectionId, MultipartFile videoFile) {
        User teacher = userService.getAuthenticatedUser();
        OperationHandler<VideoCreationContext> createOperationHandler = videoCreationChainBuilder.build();
        VideoCreationContext context = createVideoRequestContext(request, courseId, sectionId, videoFile, teacher);
        Video video = createVideoProcess(createOperationHandler, context);
        log.info("Video created successfully with ID: {}", video.getId());
        return video;
    }

    private Video createVideoProcess(OperationHandler<VideoCreationContext> createOperationHandler, VideoCreationContext context) {
        createOperationHandler.handle(context);
        return context.getVideo();
    }

    private static VideoCreationContext createVideoRequestContext(CreateVideoRequest request, Long courseId, Long sectionId, MultipartFile videoFile, User teacher) {
        return VideoCreationContext.builder()
                .user(teacher)
                .request(request)
                .courseId(courseId)
                .sectionId(sectionId)
                .videoFile(videoFile)
                .build();
    }

    @Override
    public List<Video> batchCreateVideos(BatchCreateVideoRequest request, Long courseId, Long sectionId, Map<String, MultipartFile> fileMap) {
        User teacher = userService.getAuthenticatedUser();
        checkAllFilesPresent(fileMap, request.getCreateVideoRequests());
        List<Video> createdVideos = new ArrayList<>();
        OperationHandler<VideoCreationContext> createOperationHandler = videoCreationChainBuilder.build();
        for(CreateVideoRequest createVideoRequest : request.getCreateVideoRequests()){
            log.debug("Creating video with title: {}", createVideoRequest.getTitle());
            VideoCreationContext context = createVideoRequestContext(createVideoRequest, courseId, sectionId,
                    fileMap.get(createVideoRequest.getVideoFileIdentifier()), teacher);
            Video createdVideo = createVideoProcess(createOperationHandler, context);
            log.info("Video with title {} created successfully with ID: {}", createdVideo.getTitle() ,createdVideo.getId());
            createdVideos.add(createdVideo);
        }
        return createdVideos;
    }

    private void checkAllFilesPresent(Map<String, MultipartFile> fileMap, List<CreateVideoRequest> videoRequests) {
        for (CreateVideoRequest videoRequest : videoRequests) {
            String videoFileIdentifier = videoRequest.getVideoFileIdentifier();
            if (!fileMap.containsKey(videoFileIdentifier)) {
                throw new VideoCreationFailedException("Missing video file for identifier: " + videoFileIdentifier);
            }
        }
    }


    @Override
    @Transactional
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public Video updateVideo(UpdateVideoRequest request, MultipartFile videoFile, Long videoId) {
        log.debug("Updating video with ID: {}", videoId);
        User user = userService.getAuthenticatedUser();

        VideoUpdateContext context = VideoUpdateContext.builder()
                .user(user)
                .request(request)
                .videoId(videoId)
                .videoFile(videoFile)
                .build();

        OperationHandler<VideoUpdateContext> updateOperationHandler = videoUpdateChainBuilder.build();
        updateOperationHandler.handle(context);

        Video updatedVideo = context.getVideo();
        log.info("Video updated successfully with ID: {}", updatedVideo.getId());

        return updatedVideo;
    }


    @Override
    @Transactional
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public void deleteVideo(Long videoId) {
        log.debug("Deleting video with ID: {}", videoId);
        User user = userService.getAuthenticatedUser();
        VideoDeletionContext context = new VideoDeletionContext(user, videoId);
        OperationHandler<VideoDeletionContext> deleteOperationHandler = videoDeletionChainBuilder.build();
        deleteOperationHandler.handle(context);
        log.info("Video deleted successfully with ID: {}", videoId);
    }

    @Override
    public VideoDto convertToDto(Video video) {
        return videoMapper.videoToVideoDto(video);
    }


    private void doOwnerCheck(User user, Video video) {
        if(user.isTeacher()){
            Course course = video.getCourse();
            boolean isOwner = teacherCoursesRepository.existsById(
                    TeacherCoursesKey.builder()
                            .userId(user.getId())
                            .courseId(course.getId())
                            .build()
            );
            if(!isOwner) {
                throw new AccessDeniedException("User is not the owner of the course for this video");
            }
        }
    }

    private static void doAccessCheck(User user) {
        if(!user.isTeacher() && !user.isAdmin()) {
            throw new AccessDeniedException("User does not have permission to update videos");
        }
    }
}
