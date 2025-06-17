package com.logicerror.e_learning.dto;

import java.time.LocalDate;

public class UserEnrollmentDto {
    private UserDto userDto;
    private CourseDto courseDto;
    private String status;
    private int progress;
    private LocalDate date;


    public UserEnrollmentDto() {
        // Default constructor
    }

    public UserEnrollmentDto(UserDto userDto, CourseDto courseDto, String status, int progress, LocalDate date) {
        this.userDto = userDto;
        this.courseDto = courseDto;
        this.status = status;
        this.progress = progress;
        this.date = date;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public CourseDto getCourseDto() {
        return courseDto;
    }

    public void setCourseDto(CourseDto courseDto) {
        this.courseDto = courseDto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
