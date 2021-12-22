package com.everest.airline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
        @org.springframework.web.bind.annotation.ExceptionHandler(FlightsNotFoundException.class)
        public ResponseEntity<Object> errorMessage(FlightsNotFoundException f)
        {
            return new ResponseEntity<>(f.getMessage(), HttpStatus.NOT_FOUND);
        }

}
