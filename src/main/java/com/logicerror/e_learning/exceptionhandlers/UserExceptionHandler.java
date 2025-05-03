package com.logicerror.e_learning.exceptionhandlers;

import com.logicerror.e_learning.controllers.UserController;
import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.exceptions.user.RoleNotFoundException;
import com.logicerror.e_learning.exceptions.user.UserAlreadyExistsException;
import com.logicerror.e_learning.exceptions.user.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(assignableTypes = UserController.class)
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(409).body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(404).body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleRoleNotFoundException(RoleNotFoundException ex) {
        return ResponseEntity.status(404).body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        return ResponseEntity.status(409).body(new ApiResponse<>("Database constraint violation: " + ex.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .reduce((first, second) -> first + ", " + second)
                .orElse("Validation error");
        return ResponseEntity.status(400).body(new ApiResponse<>(errorMessage, null));
    }
}
