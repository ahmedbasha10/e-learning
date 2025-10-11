package com.logicerror.e_learning.sections.services;

import com.logicerror.e_learning.sections.dtos.SectionDto;
import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.sections.requests.BatchCreateSectionRequest;
import com.logicerror.e_learning.sections.requests.CreateSectionRequest;
import com.logicerror.e_learning.sections.requests.UpdateSectionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISectionService {

    Section getSectionById(Long sectionId);
    Section getSectionByTitle(Long courseId, String title);
    Page<Video> getSectionVideos(Long sectionId, Pageable pageable);
    Section createSection(CreateSectionRequest createSectionRequest, Long courseId);
    List<Section> batchCreateSections(BatchCreateSectionRequest batchCreateSectionRequest, Long courseId);
    Section updateSection(UpdateSectionRequest updateSectionRequest, Long sectionId);
    void updateSectionDuration(Long sectionId);
    void deleteSection(Long sectionId);

    SectionDto convertToDto(Section section);

}
