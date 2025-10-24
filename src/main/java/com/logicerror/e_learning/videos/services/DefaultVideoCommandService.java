package com.logicerror.e_learning.videos.services;

import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.services.user.IUserService;
import com.logicerror.e_learning.videos.entities.Video;
import com.logicerror.e_learning.videos.exceptions.VideoCreationFailedException;
import com.logicerror.e_learning.videos.requests.BatchCreateVideoRequest;
import com.logicerror.e_learning.videos.requests.CreateVideoRequest;
import com.logicerror.e_learning.videos.requests.UpdateVideoRequest;
import com.logicerror.e_learning.videos.services.models.VideoCreationChainBuilder;
import com.logicerror.e_learning.videos.services.models.VideoDeletionChainBuilder;
import com.logicerror.e_learning.videos.services.models.VideoUpdateChainBuilder;
import com.logicerror.e_learning.videos.services.operationhandlers.create.VideoCreationContext;
import com.logicerror.e_learning.videos.services.operationhandlers.delete.VideoDeletionContext;
import com.logicerror.e_learning.videos.services.operationhandlers.update.VideoUpdateContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultVideoCommandService implements VideoCommandService{
    private final IUserService userService;
    private final VideoCreationChainBuilder videoCreationChainBuilder;
    private final VideoUpdateChainBuilder videoUpdateChainBuilder;
    private final VideoDeletionChainBuilder videoDeletionChainBuilder;

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

    @Override
    @Transactional
    @PreAuthorize("hasRole('TEACHER')")
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

    private Video createVideoProcess(OperationHandler<VideoCreationContext> createOperationHandler, VideoCreationContext context) {
        createOperationHandler.handle(context);
        return context.getVideo();
    }

    private VideoCreationContext createVideoRequestContext(CreateVideoRequest request, Long courseId, Long sectionId, MultipartFile videoFile, User teacher) {
        return VideoCreationContext.builder()
                .user(teacher)
                .request(request)
                .courseId(courseId)
                .sectionId(sectionId)
                .videoFile(videoFile)
                .build();
    }

    private void checkAllFilesPresent(Map<String, MultipartFile> fileMap, List<CreateVideoRequest> videoRequests) {
        for (CreateVideoRequest videoRequest : videoRequests) {
            String videoFileIdentifier = videoRequest.getVideoFileIdentifier();
            if (!fileMap.containsKey(videoFileIdentifier)) {
                throw new VideoCreationFailedException("Missing video file for identifier: " + videoFileIdentifier);
            }
        }
    }


}
