package com.example.vote.configuration;

import com.example.vote.domain.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControlExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<Object> handleConflict(BusinessException ex, WebRequest request) {
        return ResponseEntity.status(ex.getHttpStatusCode()).body(ex.getOnlyBody());
    }
}
