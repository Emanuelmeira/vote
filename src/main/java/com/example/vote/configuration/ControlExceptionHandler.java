package com.example.vote.configuration;

import com.example.vote.domain.enums.DefaultValues;
import com.example.vote.domain.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControlExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<Object> handleConflict(BusinessException ex, WebRequest request) {
        return ResponseEntity.status(ex.getHttpStatusCode()).body(ex.getOnlyBody());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validationError(MethodArgumentNotValidException exMethod) {

        BindingResult bindingResult = exMethod.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<String> fieldErrorDtos = fieldErrors.stream()
                .map(f -> "{'".concat(f.getField()).concat("':'").concat(f.getDefaultMessage()).concat("'}")).map(String::new)
                .collect(Collectors.toList());

        BusinessException ex = new BusinessException();
        ex.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        ex.setMessage("Erros: "+fieldErrorDtos);

        return ResponseEntity.status(ex.getHttpStatusCode()).body(ex.getOnlyBody());
    }
}
