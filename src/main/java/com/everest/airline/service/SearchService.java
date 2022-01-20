package com.everest.airline.service;

import com.everest.airline.enums.ClassType;
import com.everest.airline.model.Flight;
//import com.everest.airline.utils.FileHandler;
import com.everest.airline.utils.FileHandler;
import com.everest.airline.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    FileHandler fileHandler;
    @Autowired
    Validator validator;
    @Value("${FILEPATH}")
    private String filePath;

    public List<Flight> searchFlights(String from, String to, String departureDate, String classType, int noOfPassengers) throws IOException {
        File directory = new File(filePath);
        File[] directoryListing = directory.listFiles();
        if (validator.isStringValid(from) || validator.isStringValid(to) || validator.isStringValid(departureDate) || validator.areStringsEqual(from, to))
            throw new IllegalArgumentException("Arguments are invalid");
        if (directoryListing == null) throw new FileNotFoundException("No files found");
        Arrays.sort(directoryListing);
        List<Flight> flightData;
        flightData = fileHandler.filterData(from, to, departureDate);
        flightData = flightData.stream().filter(flight -> flight.getClassType(ClassType.valueOf(classType)).getAvailableSeats() >= noOfPassengers).collect(Collectors.toList());
        return flightData;
    }
}
