package com.logicerror.e_learning.videos.services;

import com.logicerror.e_learning.videos.entities.Video;

import java.util.List;

public interface VideoQueryService {
    Video getVideoById(Long id);
    Video getVideoByTitleAndCourse(String title, Long courseId);
    Video getVideoByTitleAndSection(String title, Long sectionId);
    List<Video> getCourseVideos(Long courseId);
}
