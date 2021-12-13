package com.everest.airline.controller;

import com.everest.airline.exception.FlightsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(FlightsNotFoundException.class)
    public ResponseEntity<Object> errorMessage(FlightsNotFoundException f)
    {
        return new ResponseEntity<>(f.getMessage(), HttpStatus.NOT_FOUND);
    }

}
