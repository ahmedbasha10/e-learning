package com.logicerror.e_learning.videos.requests;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateVideoRequest {
    @Size(min = 10, max = 300, message = "Title must be between 10 and 300 characters")
    private String title;
}
