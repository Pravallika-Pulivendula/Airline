package com.everest.airline.service;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

@Component
public class FlightService {

    public long getNextFlightNumber() throws FileNotFoundException {
        File directory = new File("src/main/java/com/everest/airline/flights");
        File[] files = directory.listFiles();
        if(files == null) throw new FileNotFoundException("No files found");
        if(files.length == 0) return 1001;
        Arrays.sort(files);
        return Long.parseLong(files[files.length - 1].getName()) + 1;
    }
}
