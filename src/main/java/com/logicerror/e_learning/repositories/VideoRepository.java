package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.entities.course.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
