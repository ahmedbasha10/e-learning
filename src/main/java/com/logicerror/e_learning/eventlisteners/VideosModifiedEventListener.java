package com.logicerror.e_learning.eventlisteners;

import com.logicerror.e_learning.events.VideosModifiedEvent;
import com.logicerror.e_learning.services.course.CourseService;
import com.logicerror.e_learning.services.section.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideosModifiedEventListener {

    private final CourseService courseService;
    private final SectionService sectionService;

    @EventListener(VideosModifiedEvent.class)
    public void handleVideosModifiedEvent(VideosModifiedEvent event) {
        sectionService.updateSectionDuration(event.getSectionId());
        courseService.updateCourseDuration(event.getCourseId());
    }

}
