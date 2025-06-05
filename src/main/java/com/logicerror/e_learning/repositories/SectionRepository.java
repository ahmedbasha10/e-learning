package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.entities.course.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {
    Optional<Section> findByTitle(String title);

    @Query("SELECT s FROM Section s JOIN s.course c WHERE c.id = :courseId AND s.title = :title")
    Optional<Section> findByTitleWithCourseId(Long courseId, String title);

    @Query("SELECT COUNT(c) > 0 FROM Course c LEFT JOIN c.sections s WHERE c.id = :courseId AND s.title = :sectionTitle")
    boolean existsByCourseIdAndTitle(@Param("courseId") Long courseId, @Param("sectionTitle") String sectionTitle);

    @Query("SELECT COUNT(c) > 0 FROM Course c LEFT JOIN c.sections s WHERE c.id = :courseId AND s.order = :sectionOrder")
    boolean existsByCourseIdAndOrder(@Param("courseId") Long courseId, @Param("sectionOrder") int sectionOrder);


//    @Query(value = """
//            SELECT EXISTS (SELECT 1 FROM section s where s.course.id = :courseId AND s.title = :sectionTitle) AS titleExists,
//            EXISTS (SELECT 1 FROM section s where s.course.id = :courseId AND s.section_order = :sectionOrder) AS orderExists
//            """)
//    Map<String, Boolean> checkDuplicates(@Param("courseId") Long courseId, @Param("sectionTitle") String sectionTitle, @Param("sectionOrder") int sectionOrder);
}
