package com.logicerror.e_learning.sections.projections;

import com.logicerror.e_learning.dto.ProjectionDTOMapper;
import com.logicerror.e_learning.dto.VideoPreviewProjection;
import com.logicerror.e_learning.sections.dtos.SectionDto;

import java.util.List;
import java.util.stream.Collectors;

public interface SectionPreviewProjectionDTOMapper extends ProjectionDTOMapper<SectionDto> {
    Long getId();
    String getTitle();
    Integer getDuration();
    Integer getOrder();
    List<VideoPreviewProjection> getVideos();

    default SectionDto toDTO() {
        return SectionDto.builder()
                .id(getId())
                .title(getTitle())
                .duration(getDuration())
                .order(getOrder())
                .videos(getVideos().stream().map(VideoPreviewProjection::toDTO).collect(Collectors.toSet()))
                .build();
    }
}
