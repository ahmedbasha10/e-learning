package com.logicerror.e_learning.exceptionhandlers;

import com.logicerror.e_learning.controllers.VideoController;
import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.exceptions.general.ResourceAlreadyExistsException;
import com.logicerror.e_learning.exceptions.general.ResourceCreationFailedException;
import com.logicerror.e_learning.exceptions.general.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = VideoController.class)
public class VideoExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(ResourceCreationFailedException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceCreationFailedException(ResourceCreationFailedException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(ex.getMessage(), null));
    }
}
