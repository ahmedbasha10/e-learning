package com.logicerror.e_learning.videos.projections;

import com.logicerror.e_learning.dto.ProjectionDTOMapper;
import com.logicerror.e_learning.videos.dtos.VideoDto;

public interface VideoProjection extends ProjectionDTOMapper<VideoDto> {
    Long getId();
    String getTitle();
    String getUrl();
    Integer getDuration();

    default VideoDto toDTO() {
        return VideoDto.builder()
                .id(getId())
                .title(getTitle())
                .url(getUrl())
                .duration(getDuration())
                .build();
    }
}
