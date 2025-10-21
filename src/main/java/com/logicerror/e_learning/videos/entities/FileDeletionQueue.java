package com.logicerror.e_learning.videos.entities;

import com.logicerror.e_learning.constants.DeletionStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "file_deletion_queue")
public class FileDeletionQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;
    private Integer retryCount;

    @Enumerated(EnumType.STRING)
    private DeletionStatus status;

    public FileDeletionQueue() {
    }

    public FileDeletionQueue(String filePath, Integer retryCount, DeletionStatus status) {
        this.filePath = filePath;
        this.retryCount = retryCount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public DeletionStatus getStatus() {
        return status;
    }

    public void setStatus(DeletionStatus status) {
        this.status = status;
    }
}
