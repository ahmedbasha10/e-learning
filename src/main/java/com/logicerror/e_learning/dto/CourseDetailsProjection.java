package com.logicerror.e_learning.dto;


import java.util.Set;

public interface CourseDetailsProjection {
    Long getId();
    String getTitle();
    String getDescription();
    String getCategory();
    String getLevel();
    String getImageUrl();
    Integer getDuration();
    Integer getPrice();
    Integer getStudentsCount();
    Set<TeacherProjection> getTeachers();
    Set<SectionProjection> getSections();
}

