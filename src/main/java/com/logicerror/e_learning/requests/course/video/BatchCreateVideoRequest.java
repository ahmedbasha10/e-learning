package com.logicerror.e_learning.requests.course.video;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.logicerror.e_learning.validators.UniqueFields;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@UniqueFields(
        message = "Duplicate videos titles or video file identifiers are not allowed",
        fields = {"title", "videoFileIdentifier"},
        collectionProperty = "createVideoRequests")
public class BatchCreateVideoRequest {
    @NotNull(message = "Video create requests cannot be null")
    @Size(min = 1, message = "At least one video request is required")
    @JsonAlias("videos")
    @Valid
    private final List<CreateVideoRequest> createVideoRequests;
}
