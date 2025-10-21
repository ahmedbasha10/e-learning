package com.logicerror.e_learning.videos.services.models;

import com.logicerror.e_learning.services.AbstractOperationHandlerChainBuilder;
import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.videos.services.operationhandlers.create.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.List;

@RequiredArgsConstructor
@Component
public class VideoCreationChainBuilder extends AbstractOperationHandlerChainBuilder<VideoCreationContext> {
    private final VideoCreateAuthorizationHandler videoCreateAuthorizationHandler;
    private final VideoCreateValidationHandler videoCreateValidationHandler;
    private final PreVideoCreationHandler preVideoCreationHandler;
    private final UploadVideoFileHandler uploadVideoFileHandler;
    private final PostVideoFileUploadHandler postVideoFileUploadHandler;
    private final VideoCreationHandler videoCreationHandler;
    private final PostVideoCreationHandler postVideoCreationHandler;

    @Override
    protected List<OperationHandler<VideoCreationContext>> getOperationHandlers() {
        return List.of(
                videoCreateAuthorizationHandler,
                videoCreateValidationHandler,
                preVideoCreationHandler,
                uploadVideoFileHandler,
                postVideoFileUploadHandler,
                videoCreationHandler,
                postVideoCreationHandler
        );
    }
}
