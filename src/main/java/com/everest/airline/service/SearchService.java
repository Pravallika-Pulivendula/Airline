package com.everest.airline.service;

import com.everest.airline.model.Flight;


import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SearchService {
    private ArrayList<Flight> flightData;

    public List<Flight> searchDepartureDate(String from, String to, String departureDate) throws IOException {
        FileInputStream stream = new FileInputStream("/Users/Pravallika/Documents/Assignments/airlines/src/main/java/com/everest/airline/data.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String strLine;
        flightData = new ArrayList<>();
        while ((strLine = br.readLine()) != null) {
            String[] flightDetails;
            flightDetails = strLine.split(",");
            if (flightDetails[1].equals(from) && flightDetails[2].equals(to) && flightDetails[3].equals(departureDate) && Integer.parseInt(flightDetails[6]) != 0)
                flightData.add(new Flight(Long.parseLong(flightDetails[0]), flightDetails[1], flightDetails[2], LocalDate.parse(flightDetails[3]), LocalTime.parse(flightDetails[4]), LocalTime.parse(flightDetails[5]), Integer.parseInt(flightDetails[6])));
        }
        stream.close();
        return flightData;
    }

    public List<Flight> getFlightData() {
        return flightData.stream().filter(flight -> flight.getAvailableSeats() != 0).collect(Collectors.toList());
    }

}
