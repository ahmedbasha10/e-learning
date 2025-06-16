package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.entities.course.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {
    Optional<Video> findByTitleAndCourseId(String title, Long courseId);

    Optional<Video> findByTitleAndSectionId(String title, Long sectionId);

    Page<Video> findAllBySectionId(Long sectionId, Pageable pageable);

    boolean existsByTitleAndCourseId(String title, Long courseId);
}
