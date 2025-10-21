package com.logicerror.e_learning.videos.repositories;

import com.logicerror.e_learning.constants.DeletionStatus;
import com.logicerror.e_learning.videos.entities.FileDeletionQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileDeletionQueueRepository extends JpaRepository<FileDeletionQueue, Long> {
    List<FileDeletionQueue> findByStatusInAndRetryCountLessThan(List<DeletionStatus> list, int retryCount);
}
