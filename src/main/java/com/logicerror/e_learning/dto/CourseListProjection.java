package com.logicerror.e_learning.dto;

public interface CourseListProjection {
    Long getId();
    String getTitle();
    String getDescription();
    String getImageUrl();
    String getTeacherFirstName();
    String getTeacherLastName();
    String getLevel();
    Integer getPrice();
    Integer getDuration();

}
