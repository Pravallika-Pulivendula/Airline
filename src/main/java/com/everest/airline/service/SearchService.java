package com.everest.airline.service;

import com.everest.airline.utils.FileHandler;
import com.everest.airline.utils.ValidateInput;
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
    @Autowired
    ValidateInput validateInput;
    public static final String FILEPATH = "src/main/java/com/everest/airline/flights";


    public List<Flight> searchFlights(String from, String to, String departureDate, String classType, int noOfPassengers) throws IOException {
        File directory = new File(FILEPATH);
        File[] directoryListing = directory.listFiles();
        if (validateInput.isStringValid(from) || validateInput.isStringValid(to) || validateInput.isStringValid(departureDate) || validateInput.areStringsEqual(from, to))
            throw new IllegalArgumentException("Arguments are invalid");
        if (directoryListing == null) throw new FileNotFoundException("No files found");
        Arrays.sort(directoryListing);
        List<Flight> flightData;
        flightData = data.filterData(from, to, departureDate);
        flightData = flightData.stream().filter(flight -> flight.getSeatType(ClassType.valueOf(classType)).getAvailableSeats() >= noOfPassengers).collect(Collectors.toList());
        return flightData;
    }
}
