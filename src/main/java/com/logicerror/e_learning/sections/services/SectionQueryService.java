package com.logicerror.e_learning.sections.services;

import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.sections.entities.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SectionQueryService {
    Section getSectionById(Long sectionId);
    Page<Video> getSectionVideos(Long sectionId, Pageable pageable);
}
