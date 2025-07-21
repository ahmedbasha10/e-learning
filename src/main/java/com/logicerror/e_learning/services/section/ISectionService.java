package com.logicerror.e_learning.services.section;

import com.logicerror.e_learning.dto.SectionDto;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.requests.course.section.CreateSectionRequest;
import com.logicerror.e_learning.requests.course.section.UpdateSectionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISectionService {

    Section getSectionById(Long sectionId);
    Section getSectionByTitle(Long courseId, String title);
    Page<Video> getSectionVideos(Long sectionId, Pageable pageable);
    Section createSection(CreateSectionRequest createSectionRequest, Long courseId);
    Section updateSection(UpdateSectionRequest updateSectionRequest, Long sectionId);
    void updateSectionDuration(Long sectionId);
    void deleteSection(Long sectionId);

    SectionDto convertToDto(Section section);

}
