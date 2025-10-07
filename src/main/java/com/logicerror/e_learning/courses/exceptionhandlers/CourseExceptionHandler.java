package com.logicerror.e_learning.courses.exceptionhandlers;

import com.logicerror.e_learning.courses.controllers.CourseController;
import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.exceptions.general.ResourceAlreadyExistsException;
import com.logicerror.e_learning.exceptions.general.ResourceCreationFailedException;
import com.logicerror.e_learning.exceptions.general.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice(assignableTypes = CourseController.class)
public class CourseExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(400).body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(404).body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        return ResponseEntity.status(409).body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(ResourceCreationFailedException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceCreationFailedException(ResourceCreationFailedException ex) {
        return ResponseEntity.status(500).body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(403).body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errorData = ex.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage()
                                : "Invalid value"
                ));
        return ResponseEntity.status(400).body(new ApiResponse<>("Invalid input data", errorData));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(500).body(new ApiResponse<>("An unexpected error occurred: " + ex.getMessage(), null));
    }
}
