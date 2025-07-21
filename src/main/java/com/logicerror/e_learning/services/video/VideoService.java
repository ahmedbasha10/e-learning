package com.logicerror.e_learning.services.video;

import com.logicerror.e_learning.config.StorageProperties;
import com.logicerror.e_learning.dto.VideoDto;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.video.VideoCreationFailedException;
import com.logicerror.e_learning.exceptions.video.VideoNotFoundException;
import com.logicerror.e_learning.mappers.VideoMapper;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import com.logicerror.e_learning.repositories.VideoRepository;
import com.logicerror.e_learning.requests.course.video.CreateVideoRequest;
import com.logicerror.e_learning.requests.course.video.UpdateVideoRequest;
import com.logicerror.e_learning.services.FileManagementService;
import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.services.course.CourseService;
import com.logicerror.e_learning.services.section.SectionService;
import com.logicerror.e_learning.services.user.IUserService;
import com.logicerror.e_learning.services.video.fields.VideoFieldsUpdateService;
import com.logicerror.e_learning.services.video.models.VideoCreationChainBuilder;
import com.logicerror.e_learning.services.video.operationhandlers.create.VideoCreationContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mp4parser.IsoFile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoService implements IVideoService {
    private final VideoRepository videoRepository;
    private final SectionService sectionService;
    private final CourseService courseService;
    private final IUserService userService;
    private final TeacherCoursesRepository teacherCoursesRepository;
    private final VideoFieldsUpdateService videoFieldsUpdateService;
    private final FileManagementService fileManagementService;
    private final StorageProperties storageProperties;
    private final VideoMapper videoMapper;
    private final VideoCreationChainBuilder videoCreationChainBuilder;

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
        VideoCreationContext context = VideoCreationContext.builder()
                .user(teacher)
                .request(request)
                .courseId(courseId)
                .sectionId(sectionId)
                .videoFile(videoFile)
                .build();
        OperationHandler<VideoCreationContext> createOperationHandler = videoCreationChainBuilder.build();
        createOperationHandler.handle(context);
        Video video = context.getVideo();
        log.info("Video created successfully with ID: {}", video.getId());
        return video;
    }


    private String buildFilePath(User teacher, Video video, MultipartFile videoFile) {
        Section videoSection = video.getSection();
        Course videoCourse = video.getCourse();
        return storageProperties.getVideoPath()
                + File.separator
                + teacher.getUsername()
                + File.separator
                + videoCourse.getTitle()
                + File.separator
                + videoSection.getTitle()
                + File.separator
                + video.getId()
                + File.separator
                + videoFile.getOriginalFilename();
    }

    private String storeFile(MultipartFile videoFile, String filePath) {
        try (InputStream input = videoFile.getInputStream()) {
            return fileManagementService.uploadFile(input, filePath);
        } catch (IOException e) {
            throw new VideoCreationFailedException("Failed to store video file: " + e.getMessage());
        }
    }

    private static void doTeacherAccessCheck(User user) {
        if(!user.isTeacher()) throw new AccessDeniedException("User does not have permission to create videos");
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public Video updateVideo(UpdateVideoRequest request, MultipartFile videoFile, Long videoId) {
        log.debug("Updating video with ID: {}", videoId);
        User user = userService.getAuthenticatedUser();

        doAccessCheck(user);

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException("Video not found with id: " + videoId));

        doOwnerCheck(user, video);

        videoFieldsUpdateService.update(video, request);

        if(videoFile != null && !videoFile.isEmpty()) {
            updateVideoContent(video, videoFile);
        }

        Video updatedVideo = videoRepository.save(video);

        log.info("Video updated successfully with ID: {}", updatedVideo.getId());

        sectionService.updateSectionDuration(updatedVideo.getSection().getId());
        courseService.updateCourseDuration(updatedVideo.getCourse().getId());

        return updatedVideo;
    }

    @Override
    public VideoDto markVideoAsCompleted(Long videoId) {
    log.debug("Marking video as completed with ID: {}", videoId);
        User user = userService.getAuthenticatedUser();
        doAccessCheck(user);
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException("Video not found with id: " + videoId));
        doOwnerCheck(user, video);
        return null;
    }

    private void updateVideoContent(Video video, MultipartFile videoFile) {
        deleteVideoFile(video);
        String directory = buildFilePath((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                                         video,
                                         videoFile);
        String filePath = storeFile(videoFile, directory);
        int durationInMinutes = extractDurationInSeconds(filePath);
        video.setUrl(filePath);
        video.setDuration(durationInMinutes);
    }

    private void deleteVideoFile(Video video) {
        fileManagementService.deleteFile(video.getUrl());
    }


    @Override
    @Transactional
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public void deleteVideo(Long videoId) {
        log.debug("Deleting video with ID: {}", videoId);
        User user = userService.getAuthenticatedUser();
        doAccessCheck(user);
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new VideoNotFoundException("Video not found with id: " + videoId));
        doOwnerCheck(user, video);
        videoRepository.delete(video);
        deleteVideoFile(video);
        log.info("Video deleted successfully with ID: {}", videoId);
        sectionService.updateSectionDuration(video.getSection().getId());
        courseService.updateCourseDuration(video.getCourse().getId());
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

    private int extractDurationInSeconds(String filePath){
        try (IsoFile isoFile = new IsoFile(new File(filePath))){
            return (int) Math.ceil(
                    (double) isoFile.getMovieBox().getMovieHeaderBox().getDuration() /
                    isoFile.getMovieBox().getMovieHeaderBox().getTimescale());
        } catch (IOException e) {
            log.error("Error extracting video duration from file: {}", filePath, e);
            throw new RuntimeException("Failed to extract video duration", e);
        }
    }
}
