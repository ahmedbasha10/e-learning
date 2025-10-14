package com.logicerror.e_learning.sections.repositories;

import com.logicerror.e_learning.sections.entities.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {

    Page<Section> findAllByCourseId(Long courseId, Pageable pageable);
    List<Section> findAllByCourseId(Long courseId);

    @Query("""
            SELECT SUM(COALESCE(v.duration, 0))
            FROM Section s INNER JOIN s.videos v
            ON s.id = v.section.id
            WHERE s.id = :sectionId
            GROUP BY s.id
            """)
    Integer calculateSectionDuration(Long sectionId);

    @Query("SELECT COUNT(c) > 0 FROM Course c LEFT JOIN c.sections s WHERE c.id = :courseId AND s.title = :sectionTitle")
    boolean existsByCourseIdAndTitle(@Param("courseId") Long courseId, @Param("sectionTitle") String sectionTitle);

    @Query("SELECT COUNT(c) > 0 FROM Course c LEFT JOIN c.sections s WHERE c.id = :courseId AND s.order = :sectionOrder")
    boolean existsByCourseIdAndOrder(@Param("courseId") Long courseId, @Param("sectionOrder") int sectionOrder);

    @Modifying
    @Query("UPDATE Section s SET s.duration = :duration WHERE s.id = :sectionId")
    void updateSectionDuration(@Param("sectionId") Long sectionId, @Param("duration") Integer duration);
}
