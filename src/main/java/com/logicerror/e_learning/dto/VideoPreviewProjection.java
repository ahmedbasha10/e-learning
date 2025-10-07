package com.logicerror.e_learning.dto;

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
