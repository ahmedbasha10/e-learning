package com.logicerror.e_learning.dto;

import java.util.List;

public interface SectionProjection {
    Long getId();
    String getTitle();
    Integer getDuration();
    Integer getOrder();
    List<VideoProjection> getVideos();
}
