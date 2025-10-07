package com.logicerror.e_learning.dto;


import lombok.Builder;

import java.util.Set;

@Builder
public class CourseDto {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String level;
    private String imageUrl;
    private Integer duration;
    private Integer price;
    private UserDto teacher;
    private Set<SectionDto> sections;
    private Integer studentsCount;

    public CourseDto() {
    }

    public CourseDto(Long id, String title, String description, String category, String level, String imageUrl, Integer duration, Integer price, UserDto teacher, Set<SectionDto> sections, Integer studentsCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.level = level;
        this.imageUrl = imageUrl;
        this.duration = duration;
        this.price = price;
        this.teacher = teacher;
        this.sections = sections;
        this.studentsCount = studentsCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public UserDto getTeacher() {
        return teacher;
    }

    public void setTeacher(UserDto teacher) {
        this.teacher = teacher;
    }

    public Set<SectionDto> getSections() {
        return sections;
    }

    public void setSections(Set<SectionDto> sections) {
        this.sections = sections;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount = studentsCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
