package com.comak.sce.config;

import com.comak.sce.exception.AccountException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException exception) {

        List<String> errors = exception.getBindingResult()
            .getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .toList();

        return new ResponseEntity<>( createResponseBody(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountException.class)
    public final ResponseEntity<Map<String, Object>> handleGeneralExceptions(AccountException exception) {
        List<String> errors = Collections.singletonList(exception.getMessage());
        return new ResponseEntity<>(createResponseBody(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Map<String, Object>> handleRuntimeExceptions(RuntimeException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(createResponseBody(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> createResponseBody( List<String> errors ) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", new Date() );
        responseBody.put("errors", errors);
        return  responseBody;
    }
}
