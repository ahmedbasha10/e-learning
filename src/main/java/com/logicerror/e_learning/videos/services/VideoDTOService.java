package com.logicerror.e_learning.videos.services;

import com.logicerror.e_learning.videos.dtos.VideoDto;
import com.logicerror.e_learning.videos.entities.Video;
import com.logicerror.e_learning.videos.mappers.VideoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoDTOService {
    private final VideoMapper videoMapper;

    public VideoDto convertToDto(Video video) {
        return videoMapper.videoToVideoDto(video);
    }
}
