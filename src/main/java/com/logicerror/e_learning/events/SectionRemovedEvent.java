package com.logicerror.e_learning.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SectionRemovedEvent extends ApplicationEvent {
    private final Long courseId;

    public SectionRemovedEvent(Object source, Long courseId) {
        super(source);
        this.courseId = courseId;
    }
}
