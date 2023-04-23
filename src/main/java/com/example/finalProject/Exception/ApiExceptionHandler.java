package com.example.finalProject.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    //3 create
    @ExceptionHandler(value = {InvalidStatementException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiException e){
        //1. create payload containing exception and details
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z")));
        //2. Return response entity
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}