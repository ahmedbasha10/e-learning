package com.logicerror.e_learning.sections.services;

import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.repositories.VideoRepository;
import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.sections.exceptions.SectionNotFoundException;
import com.logicerror.e_learning.sections.repositories.SectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultSectionQueryService implements SectionQueryService{
    private final SectionRepository sectionRepository;
    private final VideoRepository videoRepository;

    @Override
    public Section getSectionById(Long sectionId) {
        log.debug("Fetching section with ID: {}", sectionId);
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new SectionNotFoundException("Section not found with id: " + sectionId));
        log.info("Section found: {}", section.getTitle());
        return section;
    }

    @Override
    public Page<Video> getSectionVideos(Long sectionId, Pageable pageable) {
        Assert.notNull(sectionId, "Section ID must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        log.debug("Fetching videos for section with ID: {}", sectionId);
        return videoRepository.findAllBySectionId(sectionId, pageable);
    }
}
