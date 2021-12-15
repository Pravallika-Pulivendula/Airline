package com.everest.airline.service;

import com.everest.airline.Data;
import com.everest.airline.model.Flight;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SeatService {
    String filePath = "/Users/Pravallika/Documents/Assignments/airlines/src/main/java/com/everest/airline/flights";
    File directory = new File(filePath);
    public double updateAvailableSeats(long number, int noOfPassengers,String classType,List<Flight> flightsData) throws IOException {
        Flight flights = flightsData.stream().filter(flight -> flight.getNumber() == number).collect(Collectors.toList()).get(0);
        String toReplace = flights.getAvailableSeats()+","+ flights.getFirstClassSeats()+","+ flights.getSecondClassSeats()+","+ flights.getEconomicClassSeats();
        flights.updateAvailableSeats(noOfPassengers);
        double totalFair = filterSeats(noOfPassengers,classType,flights);
        String toBeReplaced = flights.getAvailableSeats()+","+ flights.getFirstClassSeats()+","+ flights.getSecondClassSeats()+","+ flights.getEconomicClassSeats();
        Data.writeDataToFile(number,toReplace,toBeReplaced);
        return totalFair;
    }

    public double filterSeats(int noOfPassengers,String classType,Flight flight)
    {
        double totalFair = 0;
        if(classType.equals("Economic")) totalFair = flight.updateEconomicClassSeats(noOfPassengers);
        if(classType.equals("First")) totalFair =flight.updateFirstClassSeats(noOfPassengers);
        if(classType.equals("Second")) totalFair = flight.updateSecondClassSeats(noOfPassengers);
        return totalFair;
    }
}
