package com.logicerror.e_learning.videos.services.fields;

import com.logicerror.e_learning.videos.entities.Video;
import com.logicerror.e_learning.videos.requests.UpdateVideoRequest;
import com.logicerror.e_learning.services.Updater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoFieldsUpdateService implements Updater<Video, UpdateVideoRequest> {
    private final List<VideoFieldUpdater> fieldUpdaters;

    @Override
    public void update(Video entity, UpdateVideoRequest updateRequest) {
        fieldUpdaters
                .forEach(fieldUpdater -> fieldUpdater.updateField(entity, updateRequest));
    }
}
