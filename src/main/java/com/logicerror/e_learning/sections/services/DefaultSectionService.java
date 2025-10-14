package com.logicerror.e_learning.sections.services;

import com.logicerror.e_learning.sections.dtos.SectionDto;
import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.sections.requests.BatchCreateSectionRequest;
import com.logicerror.e_learning.sections.requests.CreateSectionRequest;
import com.logicerror.e_learning.sections.requests.UpdateSectionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultSectionService implements SectionService {
    private final SectionCommandService sectionCommandService;
    private final SectionDTOService sectionDTOService;


    @Override
    @PreAuthorize("hasRole('TEACHER')")
    @Transactional
    public SectionDto createSection(CreateSectionRequest createSectionRequest, Long courseId) {
        Section section = sectionCommandService.createSection(createSectionRequest, courseId);
        return sectionDTOService.toDto(section);
    }

    @Override
    @PreAuthorize("hasRole('TEACHER')")
    @Transactional
    public List<SectionDto> batchCreateSections(BatchCreateSectionRequest batchCreateSectionRequest, Long courseId) {
        return sectionCommandService.batchCreateSections(batchCreateSectionRequest, courseId)
                .stream()
                .map(sectionDTOService::toDto)
                .toList();
    }


    @Override
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Transactional
    public SectionDto updateSection(UpdateSectionRequest updateSectionRequest, Long sectionId) {
        return sectionDTOService.toDto(sectionCommandService.updateSection(updateSectionRequest, sectionId));
    }

    @Override
    @Transactional
    public void updateSectionDuration(Long sectionId) {
        sectionCommandService.updateSectionDuration(sectionId);
    }

    @Override
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Transactional
    public void deleteSection(Long sectionId) {
        sectionCommandService.deleteSection(sectionId);
    }
}
