package com.logicerror.e_learning.services.section;

import com.logicerror.e_learning.dto.SectionDto;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.requests.course.section.CreateSectionRequest;

public interface ISectionService {

    Section getSectionById(Long sectionId);
    Section getSectionByTitle(String title);
    Section createSection(CreateSectionRequest createSectionRequest, Long courseId);

    SectionDto convertToDto(Section section);
}
