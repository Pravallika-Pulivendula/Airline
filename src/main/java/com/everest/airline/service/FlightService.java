package com.everest.airline.service;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;

@Component
public class FlightService {

    public long getNextFlightNumber() {
        File directory = new File("src/main/java/com/everest/airline/flights");
        File[] files = directory.listFiles();
        Arrays.sort(files);
        return Long.parseLong(files[files.length - 1].getName()) + 1;
    }
}
