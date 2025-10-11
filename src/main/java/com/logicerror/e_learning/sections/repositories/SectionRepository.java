package com.logicerror.e_learning.sections.repositories;

import com.logicerror.e_learning.sections.entities.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {

    Page<Section> findAllByCourseId(Long courseId, Pageable pageable);
    List<Section> findAllByCourseId(Long courseId);

    @Query("SELECT COUNT(c) > 0 FROM Course c LEFT JOIN c.sections s WHERE c.id = :courseId AND s.title = :sectionTitle")
    boolean existsByCourseIdAndTitle(@Param("courseId") Long courseId, @Param("sectionTitle") String sectionTitle);

    @Query("SELECT COUNT(c) > 0 FROM Course c LEFT JOIN c.sections s WHERE c.id = :courseId AND s.order = :sectionOrder")
    boolean existsByCourseIdAndOrder(@Param("courseId") Long courseId, @Param("sectionOrder") int sectionOrder);
}
