package com.logicerror.e_learning.videos.services.operationhandlers.create;

import com.logicerror.e_learning.videos.entities.Video;
import org.mp4parser.IsoFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class PostVideoFileUploadHandler extends BaseVideoCreationHandler{
    @Override
    protected void processRequest(VideoCreationContext context) {
        Video video = context.getVideo();
        video.setDuration(extractDurationInSeconds(video.getUrl()));
    }

    private int extractDurationInSeconds(String filePath){
        try (IsoFile isoFile = new IsoFile(new File(filePath))){
            return (int) Math.ceil(
                    (double) isoFile.getMovieBox().getMovieHeaderBox().getDuration() /
                            isoFile.getMovieBox().getMovieHeaderBox().getTimescale());
        } catch (IOException e) {
            logger.error("Error extracting video duration from file: {}", filePath, e);
            throw new RuntimeException("Failed to extract video duration", e);
        }
    }
}
