package com.logicerror.e_learning.videos.repositories;

import com.logicerror.e_learning.videos.entities.VideoCompletion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoCompletionRepository extends JpaRepository<VideoCompletion, Long> {
    Optional<VideoCompletion> findByUserIdAndVideoId(Long userId, Long videoId);
    List<VideoCompletion> findByUserIdAndVideo_CourseId(Long userId, Long courseId);
    int countByUserIdAndVideo_CourseId(Long userId, Long courseId);
    boolean existsByUserIdAndVideoId(Long userId, Long videoId);

    List<VideoCompletion> findByVideoId(Long videoId);

    void deleteByVideoId(Long videoId);
}
