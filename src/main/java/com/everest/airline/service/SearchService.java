package com.everest.airline.service;

import com.everest.airline.Data;
import com.everest.airline.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchService {
    @Autowired
    Data data;

    private final File directory = new File("/Users/Pravallika/Documents/Assignments/airlines/src/main/java/com/everest/airline/flights");
    private final File[] directoryListing = directory.listFiles();

    public List<Flight> searchFlights(String from, String to, String departureDate, String classType, int noOfPassengers) throws FileNotFoundException {
        assert directoryListing != null;
        Arrays.sort(directoryListing);
        ArrayList<Flight> flightData = new ArrayList<>();
        data.filterFlightsData(from, to, departureDate, noOfPassengers, directoryListing, flightData);
        flightData = (ArrayList<Flight>) filterSearch(classType, flightData, noOfPassengers);
        return flightData;
    }

    public List<Flight> filterSearch(String classType, List<Flight> flights, int noOfPassengers) {
        if (classType.equals("Economic"))
            flights = flights.stream().filter(flight -> flight.getEconomicClassSeats() >= noOfPassengers).collect(Collectors.toList());
        if (classType.equals("First"))
            flights = flights.stream().filter(flight -> flight.getFirstClassSeats() >= noOfPassengers).collect(Collectors.toList());
        if (classType.equals("Second"))
            flights = flights.stream().filter(flight -> flight.getSecondClassSeats() >= noOfPassengers).collect(Collectors.toList());
        return flights;
    }

}
