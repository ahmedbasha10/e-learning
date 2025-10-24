package com.logicerror.e_learning.videos.services;

import com.logicerror.e_learning.videos.entities.Video;
import com.logicerror.e_learning.videos.exceptions.VideoNotFoundException;
import com.logicerror.e_learning.videos.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultVideoQueryService implements VideoQueryService{
    private final VideoRepository videoRepository;

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
}
