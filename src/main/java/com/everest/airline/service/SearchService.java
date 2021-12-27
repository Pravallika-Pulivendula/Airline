package com.everest.airline.service;

import com.everest.airline.FileHandler;
import com.everest.airline.enums.ClassType;
import com.everest.airline.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchService {
    @Autowired
    FileHandler data;

    File directory = new File("src/main/java/com/everest/airline/flights");
    File[] directoryListing = directory.listFiles();

    public List<Flight> searchFlights(String from, String to, String departureDate, String classType, int noOfPassengers) throws IOException {
        if (isStringValid(from) || isStringValid(to) || isStringValid(departureDate))
            throw new IllegalArgumentException("Arguments cannot be null");
        if (directoryListing == null) throw new FileNotFoundException("No files found");
        Arrays.sort(directoryListing);
        List<Flight> flightData;
        flightData = data.filterData(from, to, departureDate, noOfPassengers);
        flightData = flightData.stream().filter(flight -> flight.getSeatType(ClassType.valueOf(classType)).getAvailableSeats() >= noOfPassengers).collect(Collectors.toList());
        return flightData;
    }

    public boolean isStringValid(String string) {
        return string == null || string.trim().isEmpty();
    }
}
