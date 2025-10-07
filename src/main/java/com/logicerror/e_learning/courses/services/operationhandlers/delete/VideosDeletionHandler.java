package com.logicerror.e_learning.courses.services.operationhandlers.delete;

import com.logicerror.e_learning.constants.DeletionStatus;
import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.entities.course.FileDeletionQueue;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.repositories.FileDeletionQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class VideosDeletionHandler extends BaseCourseDeleteHandler {
    private final FileDeletionQueueRepository fileDeletionQueueRepository;

    @Override
    protected void processRequest(CourseDeleteContext context) {
        logger.debug("Deleting videos for course with ID: {}", context.getCourseId());
        Course course = context.getDeletedCourse();
        List<Video> videos = course.getSections().stream()
                .map(Section::getVideos)
                .flatMap(Set::stream)
                .toList();

        List<FileDeletionQueue> deletionQueue = videos.stream()
                .map(video ->
                        new FileDeletionQueue(video.getUrl(), 0, DeletionStatus.PENDING))
                .toList();

        if (!deletionQueue.isEmpty()) {
            logger.debug("Adding {} videos to deletion queue for course with ID: {}", deletionQueue.size(), context.getCourseId());
            fileDeletionQueueRepository.saveAll(deletionQueue);
        } else {
            logger.debug("No videos found for course with ID: {}", context.getCourseId());
        }
    }
}
