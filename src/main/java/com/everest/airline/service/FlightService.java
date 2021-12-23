package com.everest.airline.service;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;

@Component
public class FlightService {
    File directory = new File("src/main/java/com/everest/airline/flights");
    File[] files = directory.listFiles();

    public long getNextFlightNumber() {
        long number = 0;
        Arrays.sort(files);
        if (Integer.parseInt(files[0].getName()) != 1001) number = 1001;
        else {
            for (int eachFile = 0; eachFile < files.length - 1; eachFile++)
                if (Long.parseLong(files[eachFile + 1].getName()) - Long.parseLong(files[eachFile].getName()) != 1) {
                    number = Long.parseLong(files[eachFile].getName()) + 1;
                    break;
                }
            number = number > 0 ? number : Long.parseLong(files[files.length - 1].getName()) + 1;
        }
        return number;
    }
}
