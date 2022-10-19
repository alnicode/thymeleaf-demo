package com.alnicode.contacts.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(Exception ex) {
        final Map<String, String> body = new LinkedHashMap<>();

        body.put("timestamp", timestamp());
        body.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> dataIntegrityViolationHandler(DataIntegrityViolationException ex) {
        final Map<String, String> body = new LinkedHashMap<>();

        body.put("timestamp", timestamp());
        body.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationHandler(ConstraintViolationException ex) {
        final Map<Object, Object> body = new LinkedHashMap<>();

        body.put("timestamp", timestamp());

        ex.getConstraintViolations().forEach(error -> {
            body.put(error.getPropertyPath(), error.getMessage());
            body.put("invalidRequest", error.getInvalidValue());
        });

        return ResponseEntity.badRequest().body(body);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final Map<String, String> body = new LinkedHashMap<>();

        body.put("timestamp", timestamp());

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> body.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(body);
    }

    private String timestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
    }

}
