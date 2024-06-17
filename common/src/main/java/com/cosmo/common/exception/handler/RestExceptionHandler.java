package com.cosmo.common.exception.handler;

import com.cosmo.common.exception.*;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    protected ApiResponse<Object> handleNotFoundException(NotFoundException ex) {
        log.error("------- RESOURCE NOT FOUND EXCEPTION -------");
        return ResponseUtil.getNotFoundResponse(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        ApiResponse<Object> response = ResponseUtil.getBeanValidationFailureResponse(errorMessage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleBadRequestException(BadRequestException ex) {
        log.error("------- BAD REQUEST EXCEPTION -------");
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        apiResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<String>> handleConflictException(ConflictException ex) {
        log.error("------- CONFLICT EXCEPTION -------");
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setHttpStatus(HttpStatus.CONFLICT);
        apiResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("------- RESOURCE NOT FOUND EXCEPTION -------");
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
        apiResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidInputException(InvalidInputException ex) {
        log.error("------- INVALID INPUT EXCEPTION -------");
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        apiResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }




}
