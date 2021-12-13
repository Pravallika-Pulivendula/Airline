package com.everest.airline.exception;


public class FlightsNotFoundException extends RuntimeException {
    public FlightsNotFoundException()
    {
        super("No flights found!");
    }
}
