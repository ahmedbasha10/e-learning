package com.logicerror.e_learning.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class VideosModifiedEvent extends ApplicationEvent {
    private final Long courseId;
    private final Long sectionId;

    public VideosModifiedEvent(Object source, Long courseId, Long sectionId) {
        super(source);
        this.courseId = courseId;
        this.sectionId = sectionId;
    }
}
