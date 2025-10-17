package com.logicerror.e_learning.sections.exceptionhandlers;

import com.logicerror.e_learning.sections.controllers.SectionController;
import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.exceptions.general.ResourceAlreadyExistsException;
import com.logicerror.e_learning.exceptions.general.ResourceCreationFailedException;
import com.logicerror.e_learning.exceptions.general.ResourceNotFoundException;
import com.logicerror.e_learning.sections.exceptions.SectionCreationFailedException;
import com.logicerror.e_learning.sections.exceptions.SectionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice(assignableTypes = SectionController.class)
@Slf4j
public class SectionExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleSectionNotFoundException(SectionNotFoundException ex) {
        log.error("Section not found ex: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Section Access Denied ex: ", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(ResourceCreationFailedException.class)
    public ResponseEntity<ApiResponse<Void>> handleSectionCreationFailedException(SectionCreationFailedException ex) {
        log.error("Section Creation Failed ex: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleSectionAlreadyExistsException(ResourceAlreadyExistsException ex) {
        log.error("Section Already Exists ex: ", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        log.error("Unexpected error ex: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("An unexpected error occurred: " + ex.getMessage(), null));
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
}
