package com.everest.airline.exception;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionHandler {
    @RequestMapping("/no-flights-found")
    public void printErrorMessage(){
        throw new FlightsNotFoundException();
    }
}
