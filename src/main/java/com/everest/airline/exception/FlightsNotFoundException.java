package com.everest.airline.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Logger;

//@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "No flights found")
public class FlightsNotFoundException extends ResponseStatusException {
    public FlightsNotFoundException(String message)
    {
        super(HttpStatus.NOT_FOUND,message);
        System.out.println("yes");

    }
}
