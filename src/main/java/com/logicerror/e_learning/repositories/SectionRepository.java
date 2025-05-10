package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.entities.course.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
}
