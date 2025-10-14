package com.logicerror.e_learning.sections.services;

import com.logicerror.e_learning.sections.dtos.SectionDto;
import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.sections.mappers.SectionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionDTOService {
    private final SectionMapper sectionMapper;

    public SectionDto toDto(Section section) {
        return sectionMapper.sectionToSectionDto(section);
    }
}
