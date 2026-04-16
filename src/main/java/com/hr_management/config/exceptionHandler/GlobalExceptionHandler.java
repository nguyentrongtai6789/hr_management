package com.hr_management.config.exceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingRequestParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
        log.error("MissingServletRequestParameterException at {} {}", request.getMethod(), request.getRequestURI(), ex);
        return ResponseEntity.badRequest().body("Missing request parameter!");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        log.error("IllegalArgumentException at {} {}", request.getMethod(), request.getRequestURI(), ex);
        return ResponseEntity.badRequest().body("Invalid argument!");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("Validation error at {} {}", request.getMethod(), request.getRequestURI(), ex);
        return ResponseEntity.badRequest().body("Validation error!");
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDatabaseError(DataAccessException ex, HttpServletRequest request) {
        log.error("Database error at {} {}", request.getMethod(), request.getRequestURI(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleNotFound(NoResourceFoundException ex, HttpServletRequest request) {
        log.error("Resource not found at {} {}", request.getMethod(), request.getRequestURI(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found!");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        log.error("RuntimeException at {} {}", request.getMethod(), request.getRequestURI(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Some thing went wrong!");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericError(Exception ex, HttpServletRequest request) {
        log.error("Generic error at {} {}", request.getMethod(), request.getRequestURI(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some thing went wrong!");
    }
}