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

    File directory = new File("src/main/java/com/everest/airline/flights");
    File[] directoryListing = directory.listFiles();
    ValidateInput validateInput = new ValidateInput();

    public List<Flight> searchFlights(String from, String to, String departureDate, String classType, int noOfPassengers) throws IOException {
        if (validateInput.isStringValid(from) || validateInput.isStringValid(to) || validateInput.isStringValid(departureDate) || validateInput.areStringsEqual(from, to))
            throw new IllegalArgumentException("Arguments are invalid");
        try {
            if (directoryListing == null) throw new FileNotFoundException("No files found");
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
        Arrays.sort(directoryListing);
        List<Flight> flightData;
        flightData = data.filterData(from, to, departureDate, noOfPassengers);
        flightData = flightData.stream().filter(flight -> flight.getSeatType(ClassType.valueOf(classType)).getAvailableSeats() >= noOfPassengers).collect(Collectors.toList());
        return flightData;
    }
}
