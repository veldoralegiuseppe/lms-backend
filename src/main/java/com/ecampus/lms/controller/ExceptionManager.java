package com.ecampus.lms.controller;

import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionManager {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Error> badCredentialHander(BadCredentialsException e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error(e.getMessage()));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Error> entityExistsHandler(EntityExistsException e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new Error(e.getMessage()));
    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Error> genericExceptionHandler(Exception e){
//        log.error(e.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Error(e.getMessage()));
//    }

    record Error(String message){}
}
