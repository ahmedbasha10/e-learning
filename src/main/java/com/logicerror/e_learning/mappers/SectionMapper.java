package com.logicerror.e_learning.mappers;

import com.logicerror.e_learning.dto.SectionDto;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.requests.course.section.CreateSectionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SectionMapper {
    @Mapping(target = "title", source = "title")
    @Mapping(target = "duration", source = "duration")
    @Mapping(target = "order", source = "order")
    Section createSectionRequestToSection(CreateSectionRequest request);

    @Mapping(target = "id", source = "section.id")
    @Mapping(target = "title", source = "section.title")
    @Mapping(target = "order", source = "section.order")
    @Mapping(target = "duration", source = "section.duration")
    @Mapping(target = "videos", source = "section.videos")
    SectionDto sectionToSectionDto(Section section);
}
