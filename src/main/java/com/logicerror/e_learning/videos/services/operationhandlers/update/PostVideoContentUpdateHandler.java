package com.logicerror.e_learning.videos.services.operationhandlers.update;

import com.logicerror.e_learning.videos.entities.Video;
import org.mp4parser.IsoFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class PostVideoContentUpdateHandler extends BaseVideoUpdateHandler {
    @Override
    protected void processRequest(VideoUpdateContext context) {
        logger.info("Post video content update handler started for video: {}", context.getVideo().getId());
        if (context.getVideoFile() == null) {
            logger.info("Stopping post video content update handler, video content not updated for video: {}", context.getVideo().getId());
            return;
        }
        Video video = context.getVideo();
        video.setDuration(extractDurationInSeconds(video.getUrl()));
        logger.info("Post video content update handler completed for video: {}", context.getVideo().getId());
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
