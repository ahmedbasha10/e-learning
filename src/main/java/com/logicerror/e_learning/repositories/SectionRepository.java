package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.course.SectionKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, SectionKey> {
}
