package com.logicerror.e_learning.services.section;

import com.logicerror.e_learning.dto.SectionDto;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.mappers.SectionMapper;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.requests.course.section.CreateSectionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionService implements ISectionService{
    private final SectionRepository sectionRepository;
    private final SectionMapper sectionMapper;


    @Override
    public SectionDto getSectionById(Long sectionId) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Section not found"));

        return convertToDto(section);
    }

    @Override
    public SectionDto getSectionByTitle(String title) {
        Section section = sectionRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Section not found"));

        return convertToDto(section);
    }

    @Override
    public SectionDto createSection(CreateSectionRequest createSectionRequest, Long courseId) {
        return null;
    }

    @Override
    public SectionDto convertToDto(Section section) {
        return sectionMapper.sectionToSectionDto(section);
    }
}
