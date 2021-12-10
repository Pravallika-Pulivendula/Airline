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

@Component
public class SearchService {

    public List<Flight> searchFlights(String from, String to, String departureDate) throws FileNotFoundException {
        File directory = new File("/Users/Pravallika/Documents/Assignments/airlines/src/main/java/com/everest/airline/flights");
        File[] directoryListing = directory.listFiles();
        assert directoryListing != null;
        Arrays.sort(directoryListing);
        ArrayList<Flight> flightData = new ArrayList<>();
        String[] flightDetails;
        for (File eachFile : directoryListing) {
            flightDetails = new Scanner(new File(eachFile.getPath())).useDelimiter("\\Z").next().split(",");
            if (flightDetails[1].equals(from) && flightDetails[2].equals(to) && flightDetails[3].equals(departureDate))
                flightData.add(new Flight(Long.parseLong(flightDetails[0]), flightDetails[1], flightDetails[2], LocalDate.parse(flightDetails[3]), LocalTime.parse(flightDetails[4]), LocalTime.parse(flightDetails[5]), Integer.parseInt(flightDetails[6])));
        }
        return flightData;
    }

}
