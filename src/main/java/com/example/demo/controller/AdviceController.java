package com.example.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AdviceController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handle(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
