package com.logicerror.e_learning.eventlisteners;

import com.logicerror.e_learning.events.SectionRemovedEvent;
import com.logicerror.e_learning.events.VideosModifiedEvent;
import com.logicerror.e_learning.courses.services.CourseCommandService;
import com.logicerror.e_learning.services.section.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideosModifiedEventListener {

    private final CourseCommandService courseService;
    private final SectionService sectionService;

    @EventListener(VideosModifiedEvent.class)
    public void handleVideosModifiedEvent(VideosModifiedEvent event) {
        sectionService.updateSectionDuration(event.getSectionId());
        courseService.updateCourseDuration(event.getCourseId());
    }

    @EventListener(SectionRemovedEvent.class)
    public void handleSectionRemovedEvent(SectionRemovedEvent event) {
        courseService.updateCourseDuration(event.getCourseId());
    }

}
