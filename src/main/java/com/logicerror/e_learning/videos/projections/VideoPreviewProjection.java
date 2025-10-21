package com.logicerror.e_learning.videos.projections;

import com.logicerror.e_learning.dto.ProjectionDTOMapper;
import com.logicerror.e_learning.videos.dtos.VideoDto;

public interface VideoPreviewProjection extends ProjectionDTOMapper<VideoDto> {
    Long getId();
    String getTitle();
    Integer getDuration();

    default VideoDto toDTO() {
        return VideoDto.builder()
                .id(getId())
                .title(getTitle())
                .duration(getDuration())
                .build();
    }
}
