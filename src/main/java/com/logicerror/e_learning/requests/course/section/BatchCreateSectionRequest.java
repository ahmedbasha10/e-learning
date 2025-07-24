package com.logicerror.e_learning.requests.course.section;


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
        message = "Duplicate section titles or orders are not allowed",
        fields = {"title", "order"},
        collectionProperty = "createSectionRequests")
public class BatchCreateSectionRequest {
    @NotNull(message = "Section create requests cannot be null")
    @Size(min = 1, message = "At least one section request is required")
    @JsonAlias("sections")
    @Valid
    private final List<CreateSectionRequest> createSectionRequests;
}
