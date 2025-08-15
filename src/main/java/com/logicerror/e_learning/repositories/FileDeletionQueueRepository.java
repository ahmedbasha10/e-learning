package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.constants.DeletionStatus;
import com.logicerror.e_learning.entities.course.FileDeletionQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileDeletionQueueRepository extends JpaRepository<FileDeletionQueue, Long> {
    List<FileDeletionQueue> findByStatusInAndRetryCountLessThan(List<DeletionStatus> list, int retryCount);
}
