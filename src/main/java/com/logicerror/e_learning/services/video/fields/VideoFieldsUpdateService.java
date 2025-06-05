package com.logicerror.e_learning.services.video.fields;

import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.requests.course.video.UpdateVideoRequest;
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
