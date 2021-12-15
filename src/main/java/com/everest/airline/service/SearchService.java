package com.everest.airline.service;

import com.everest.airline.model.Flight;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class SearchService {

    public List<Flight> searchFlights(String from, String to, String departureDate,String classType,int noOfPassengers) throws FileNotFoundException {
        File directory = new File("/Users/Pravallika/Documents/Assignments/airlines/src/main/java/com/everest/airline/flights");
        File[] directoryListing = directory.listFiles();
        assert directoryListing != null;
        Arrays.sort(directoryListing);
        ArrayList<Flight> flightData = new ArrayList<>();
        String[] flightDetails;
        for (File eachFile : directoryListing) {
            flightDetails = new Scanner(new File(eachFile.getPath())).useDelimiter("\\Z").next().split(",");
            if (flightDetails[1].equals(from) && flightDetails[2].equals(to) && flightDetails[3].equals(departureDate) && Integer.parseInt(flightDetails[6]) >= noOfPassengers)
                flightData.add(new Flight(Long.parseLong(flightDetails[0]), flightDetails[1], flightDetails[2], LocalDate.parse(flightDetails[3]), LocalTime.parse(flightDetails[4]), LocalTime.parse(flightDetails[5]), Integer.parseInt(flightDetails[6]),Integer.parseInt(flightDetails[7]),Integer.parseInt(flightDetails[8]),Integer.parseInt(flightDetails[9]),Double.parseDouble(flightDetails[10]),Double.parseDouble(flightDetails[11]),Double.parseDouble(flightDetails[12])));
        }
        flightData = (ArrayList<Flight>) filterSearch(classType,flightData,noOfPassengers);
        return flightData;
    }

    public List<Flight> filterSearch(String classType, List<Flight> flights, int noOfPassengers){
        if(classType.equals("Economic"))
            flights = flights.stream().filter(flight -> flight.getEconomicClassSeats() >= noOfPassengers).collect(Collectors.toList());
        if(classType.equals("First"))
            flights = flights.stream().filter(flight -> flight.getFirstClassSeats() >= noOfPassengers).collect(Collectors.toList());
        if(classType.equals("Second"))
            flights = flights.stream().filter(flight -> flight.getSecondClassSeats() >= noOfPassengers).collect(Collectors.toList());
        return flights;
    }

}
