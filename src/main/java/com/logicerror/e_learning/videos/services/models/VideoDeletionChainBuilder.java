package com.logicerror.e_learning.videos.services.models;

import com.logicerror.e_learning.services.AbstractOperationHandlerChainBuilder;
import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.videos.services.operationhandlers.delete.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VideoDeletionChainBuilder extends AbstractOperationHandlerChainBuilder<VideoDeletionContext> {
    private final VideoDeletionInitializationHandler videoDeletionInitializationHandler;
    private final VideoDeletionAuthorizationHandler videoDeletionAuthorizationHandler;
    private final PreVideoDeletionHandler preVideoDeletionHandler;
    private final VideoDeletionHandler videoDeletionHandler;
    private final VideoContentDeletionHandler videoContentDeletionHandler;
    private final PostVideoDeletionHandler postVideoDeletionHandler;


    @Override
    protected List<OperationHandler<VideoDeletionContext>> getOperationHandlers() {
        return List.of(
                videoDeletionInitializationHandler,
                videoDeletionAuthorizationHandler,
                preVideoDeletionHandler,
                videoDeletionHandler,
                videoContentDeletionHandler,
                postVideoDeletionHandler
        );
    }
}
