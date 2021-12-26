package com.everest.airline.service;

import com.everest.airline.FileHandler;
import com.everest.airline.enums.ClassType;
import com.everest.airline.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
        if (directoryListing == null) throw new FileNotFoundException("No files found");
        Arrays.sort(directoryListing);
        ArrayList<Flight> flightData;
        flightData = (ArrayList<Flight>) data.filterData(from, to, departureDate, noOfPassengers, directoryListing);
        flightData = (ArrayList<Flight>) filterSearch(ClassType.valueOf(classType), flightData, noOfPassengers);
        return flightData;
    }

    public List<Flight> filterSearch(ClassType classType, List<Flight> flights, int noOfPassengers) {
        flights = flights.stream().filter(flight -> flight.getSeatType(classType).getAvailableSeats() >= noOfPassengers).collect(Collectors.toList());
        return flights;
    }
}
