package com.logicerror.e_learning.dto;

import java.util.List;

public interface CourseDetailsProjection {
    Long getId();
    String getTitle();
    String getDescription();
    String getCategory();
    String getLevel();
    String getImageUrl();
    Integer getDuration();
    Integer getPrice();
    Long getTeacherId();
    String getTeacherFirstName();
    String getTeacherLastName();
    Integer getStudentsCount();
    List<SectionDto> getSections();
}

