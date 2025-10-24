package com.logicerror.e_learning.courses.projections;

import com.logicerror.e_learning.courses.dtos.CourseDto;
import com.logicerror.e_learning.dto.ProjectionDTOMapper;
import com.logicerror.e_learning.sections.projections.SectionPreviewProjectionDTOMapper;
import com.logicerror.e_learning.dto.TeacherProjection;
import com.logicerror.e_learning.services.utils.ResourcesURLService;

import java.util.Set;
import java.util.stream.Collectors;

public interface CoursePreviewProjectionDTOMapper extends ProjectionDTOMapper<CourseDto> {
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
    Set<SectionPreviewProjectionDTOMapper> getSections();

    @Override
    default CourseDto toDTO() {
        return CourseDto.builder()
                .id(getId())
                .title(getTitle())
                .description(getDescription())
                .category(getCategory())
                .level(getLevel())
                .imageUrl(getImageUrl())
                .duration(getDuration())
                .price(getPrice())
                .studentsCount(getStudentsCount())
                .teacher(getTeachers().stream().map(TeacherProjection::toUserDto).findFirst().get())
                .sections(getSections().stream().map(SectionPreviewProjectionDTOMapper::toDTO).collect(Collectors.toSet()))
                .build();
    }
}
