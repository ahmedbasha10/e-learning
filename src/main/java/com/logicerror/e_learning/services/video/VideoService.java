package com.logicerror.e_learning.services.video;

import com.logicerror.e_learning.config.StorageProperties;
import com.logicerror.e_learning.dto.VideoDto;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.section.SectionNotFoundException;
import com.logicerror.e_learning.exceptions.video.VideoCreationFailedException;
import com.logicerror.e_learning.exceptions.video.VideoNotFoundException;
import com.logicerror.e_learning.exceptions.video.VideoTitleAlreadyExistsException;
import com.logicerror.e_learning.mappers.VideoMapper;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import com.logicerror.e_learning.repositories.VideoRepository;
import com.logicerror.e_learning.requests.course.video.CreateVideoRequest;
import com.logicerror.e_learning.requests.course.video.UpdateVideoRequest;
import com.logicerror.e_learning.services.FileManagementService;
import com.logicerror.e_learning.services.video.fields.VideoFieldsUpdateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mp4parser.IsoFile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoService implements IVideoService {
    private final VideoRepository videoRepository;
    private final SectionRepository sectionRepository;
    private final TeacherCoursesRepository teacherCoursesRepository;
    private final VideoFieldsUpdateService videoFieldsUpdateService;
    private final FileManagementService fileManagementService;
    private final StorageProperties storageProperties;
    private final VideoMapper videoMapper;

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
    @Transactional
    @PreAuthorize("hasRole('TEACHER')")
    public Video createVideo(CreateVideoRequest request, Long sectionId, MultipartFile videoFile) {
        User teacher = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        doTeacherAccessCheck(teacher);
        Section section = findSectionOrThrow(sectionId);
        doVideoExistsCheck(request.getTitle(), section.getCourse().getId());

        Video video = videoMapper.createVideoRequestToVideo(request, section, section.getCourse());
        video = videoRepository.save(video);
        if (video.getId() == null) {
            throw new VideoCreationFailedException("Failed to create video.");
        }
        String directory = buildFilePath(teacher, section, video, videoFile);
        String filePath = storeFile(videoFile, directory);
        int durationInMinutes = extractDurationInMinutes(filePath);
        video.setUrl(filePath);
        video.setDuration(durationInMinutes);
        return videoRepository.save(video);
    }

    private void doVideoExistsCheck(String title, Long courseId) {
        if(videoRepository.existsByTitleAndCourseId(title, courseId)) {
            throw new VideoTitleAlreadyExistsException("Video with title '" + title + "' already exists in course with ID: " + courseId);
        }
    }

    private Section findSectionOrThrow(Long sectionId) {
        return sectionRepository.findById(sectionId)
                .orElseThrow(() -> new SectionNotFoundException("Section not found with id: " + sectionId));
    }

    private String buildFilePath(User teacher, Section section, Video video, MultipartFile videoFile) {
        return storageProperties.getVideoPath()
                + File.separator
                + teacher.getUsername()
                + File.separator
                + section.getTitle()
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
    public Video updateVideo(UpdateVideoRequest request, Long videoId) {
        log.debug("Updating video with ID: {}", videoId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        doAccessCheck(user);

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException("Video not found with id: " + videoId));

        doOwnerCheck(user, video);

        videoFieldsUpdateService.update(video, request);

        Video updatedVideo = videoRepository.save(video);

        log.info("Video updated successfully with ID: {}", updatedVideo.getId());

        return updatedVideo;
    }


    @Override
    @Transactional
    @PreAuthorize("hasRole(TEACHER) or hasRole(ADMIN)")
    public void deleteVideo(Long videoId) {
        log.debug("Deleting video with ID: {}", videoId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.isTeacher() && !user.isAdmin()) throw new AccessDeniedException("User does not have permission to delete videos");
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new VideoNotFoundException("Video not found with id: " + videoId));
        doOwnerCheck(user, video);
        videoRepository.delete(video);
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

    private int extractDurationInMinutes(String filePath){
        try (IsoFile isoFile = new IsoFile(new File(filePath))){
            int durationInSeconds = (int) Math.ceil(
                    (double) isoFile.getMovieBox().getMovieHeaderBox().getDuration() /
                    isoFile.getMovieBox().getMovieHeaderBox().getTimescale());
            return durationInSeconds / 60;
        } catch (IOException e) {
            log.error("Error extracting video duration from file: {}", filePath, e);
            throw new RuntimeException("Failed to extract video duration", e);
        }
    }
}
