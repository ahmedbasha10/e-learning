package com.logicerror.e_learning.videos.services.models;

import com.logicerror.e_learning.services.AbstractOperationHandlerChainBuilder;
import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.videos.services.operationhandlers.update.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VideoUpdateChainBuilder extends AbstractOperationHandlerChainBuilder<VideoUpdateContext> {
    private final VideoUpdateInitializationHandler videoUpdateInitializationHandler;
    private final VideoUpdateAuthorizationHandler videoUpdateAuthorizationHandler;
    private final VideoMetaDataUpdateHandler videoMetaDataUpdateHandler;
    private final VideoContentUpdateHandler videoContentUpdateHandler;
    private final PostVideoContentUpdateHandler postVideoContentUpdateHandler;
    private final VideoUpdateHandler videoUpdateHandler;
    private final PostVideoUpdateHandler postVideoUpdateHandler;


    @Override
    protected List<OperationHandler<VideoUpdateContext>> getOperationHandlers() {
        return List.of(
                videoUpdateInitializationHandler,
                videoUpdateAuthorizationHandler,
                videoMetaDataUpdateHandler,
                videoContentUpdateHandler,
                postVideoContentUpdateHandler,
                videoUpdateHandler,
                postVideoUpdateHandler
        );
    }
}
