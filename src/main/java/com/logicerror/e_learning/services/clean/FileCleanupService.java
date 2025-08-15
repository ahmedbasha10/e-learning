package com.logicerror.e_learning.services.clean;

import com.logicerror.e_learning.constants.DeletionStatus;
import com.logicerror.e_learning.entities.course.FileDeletionQueue;
import com.logicerror.e_learning.repositories.FileDeletionQueueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileCleanupService {
    private final FileDeletionQueueRepository deletionQueueRepository;

    @Scheduled(fixedRate = 36000000)
    @Async
    public void processFileDeletions() {
        log.info("Starting file deletion process at {}", LocalDateTime.now());
        List<FileDeletionQueue> pendingDeletions = deletionQueueRepository
                .findByStatusInAndRetryCountLessThan(
                        Arrays.asList(DeletionStatus.PENDING, DeletionStatus.FAILED),
                        3
                );

        for (FileDeletionQueue deletion : pendingDeletions) {
            log.info("Processing file deletion for: {}", deletion.getFilePath());
            processFileDeletion(deletion);
        }
    }

    @Transactional
    private void processFileDeletion(FileDeletionQueue deletion) {
        try {
            // Update status to processing
            deletion.setStatus(DeletionStatus.PROCESSING);
            deletionQueueRepository.save(deletion);

            // Delete the actual file
            Path filePath = Paths.get(deletion.getFilePath());
            Files.deleteIfExists(filePath);
            // delete parent if empty
            Path parentDir = filePath.getParent();
            if (parentDir != null && Files.isDirectory(parentDir)) {
                Files.deleteIfExists(parentDir);
            }

            // Mark as completed
            deletion.setStatus(DeletionStatus.COMPLETED);
            deletionQueueRepository.save(deletion);

        } catch (IOException e) {
            log.error("Failed to delete file: {}", deletion.getFilePath(), e);
            deletion.setStatus(DeletionStatus.FAILED);
            deletion.setRetryCount(deletion.getRetryCount() + 1);
            deletionQueueRepository.save(deletion);
        }
    }
}
