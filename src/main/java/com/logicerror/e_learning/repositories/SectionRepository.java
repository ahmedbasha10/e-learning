package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.entities.course.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {
    Optional<Section> findByTitle(String title);
}
