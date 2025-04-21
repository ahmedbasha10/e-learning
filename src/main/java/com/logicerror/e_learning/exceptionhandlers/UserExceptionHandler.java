package com.logicerror.e_learning.exceptionhandlers;

import com.logicerror.e_learning.controllers.UserController;
import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.exceptions.RoleNotFoundException;
import com.logicerror.e_learning.exceptions.UserAlreadyExistsException;
import com.logicerror.e_learning.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(assignableTypes = UserController.class)
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(409).body(new ApiResponse(ex.getMessage(), null));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(404).body(new ApiResponse(ex.getMessage(), null));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponse> handleRoleNotFoundException(RoleNotFoundException ex) {
        return ResponseEntity.status(404).body(new ApiResponse(ex.getMessage(), null));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        return ResponseEntity.status(409).body(new ApiResponse("Database constraint violation: " + ex.getMessage(), null));
    }
}
