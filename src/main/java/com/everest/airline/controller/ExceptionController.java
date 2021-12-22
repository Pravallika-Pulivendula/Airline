package com.everest.airline.controller;

import com.everest.airline.exception.FlightsNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {
    @RequestMapping("/no-flights-found")
    public void printErrorMessage(){
        throw new FlightsNotFoundException();
    }
}