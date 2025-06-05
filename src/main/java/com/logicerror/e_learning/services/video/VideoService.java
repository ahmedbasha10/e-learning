package com.logicerror.e_learning.services.video;

import com.logicerror.e_learning.dto.VideoDto;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.section.SectionNotFoundException;
import com.logicerror.e_learning.exceptions.video.VideoNotFoundException;
import com.logicerror.e_learning.mappers.VideoMapper;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import com.logicerror.e_learning.repositories.VideoRepository;
import com.logicerror.e_learning.requests.course.video.CreateVideoRequest;
import com.logicerror.e_learning.requests.course.video.UpdateVideoRequest;
import com.logicerror.e_learning.services.video.fields.VideoFieldsUpdateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoService implements IVideoService {
    private final VideoRepository videoRepository;
    private final SectionRepository sectionRepository;
    private final TeacherCoursesRepository teacherCoursesRepository;
    private final VideoFieldsUpdateService videoFieldsUpdateService;
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
    @PreAuthorize("hasRole(TEACHER)")
    public Video createVideo(CreateVideoRequest request, Long sectionId) {
        log.debug("Creating video with title: '{}' for section ID: {}", request.getTitle(), sectionId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        doTeacherAccessCheck(user);

        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new SectionNotFoundException("Section not found with id: " + sectionId));

        Video video = videoMapper.createVideoRequestToVideo(request, section, section.getCourse());
        Video savedVideo = videoRepository.save(video);

        if(savedVideo.getId() == null || savedVideo.getId() <= 0) {
            log.error("Failed to create video with title: '{}' for section ID: {}", request.getTitle(), sectionId);
            throw new RuntimeException("Failed to create video");
        }
        log.info("Video created successfully with ID: {}", savedVideo.getId());
        return video;
    }

    private static void doTeacherAccessCheck(User user) {
        if(!user.isTeacher()) throw new AccessDeniedException("User does not have permission to create videos");
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole(TEACHER) or hasRole(ADMIN)")
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
}
