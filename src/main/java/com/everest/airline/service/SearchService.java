package com.everest.airline.service;

import com.everest.airline.model.Flight;


import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SearchService {
    String filePath = "/Users/Pravallika/Documents/Assignments/airlines/src/main/java/com/everest/airline/data.txt";
    private ArrayList<Flight> flightData;
    public List<Flight> searchDepartureDate(String from, String to, String departureDate) throws IOException {
        FileInputStream stream = new FileInputStream("/Users/Pravallika/Documents/Assignments/airlines/src/main/java/com/everest/airline/data.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String strLine;
//        flightData.clear();
        flightData = new ArrayList<>();
        while ((strLine = br.readLine()) != null)   {
//            System.out.println(strLine);
            String[] flightDetails;
            flightDetails = strLine.split(",");
//            System.out.println(Arrays.toString(flightDetails));
            if(flightDetails[1].equals(from) && flightDetails[2].equals(to) && flightDetails[3].equals(departureDate))
                flightData.add(new Flight(Long.parseLong(flightDetails[0]),flightDetails[1],flightDetails[2], LocalDate.parse(flightDetails[3]), LocalTime.parse(flightDetails[4]),LocalTime.parse(flightDetails[5]),Integer.parseInt(flightDetails[6])));
        }
        stream.close();
        return flightData;
    }
    public List<Flight> getFlightData()
    {
        return flightData;
    }

    public void updateAvailableSeats(long number) throws IOException {
        Flight flights = getFlightData().stream().filter(flight -> flight.getNumber() == number)
                .collect(Collectors.toList()).get(0);
        String toReplace = flights.getNumber()+","+flights.getSource()+","+flights.getDestination()+","+flights.getDepartureDate()+","+flights.getDepartTime()+","+flights.getArrivalTime()+","+flights.getAvailableSeats();
        flights.updateAvailableSeats();
        String toBeReplaced = flights.getNumber()+","+flights.getSource()+","+flights.getDestination()+","+flights.getDepartureDate()+","+flights.getDepartTime()+","+flights.getArrivalTime()+","+flights.getAvailableSeats();
        Scanner sc = new Scanner(new File(filePath));
        StringBuilder buffer = new StringBuilder();
        while (sc.hasNextLine()) {
            buffer.append(sc.nextLine()).append(System.lineSeparator());
        }
        String fileContents = buffer.toString();
        sc.close();
        fileContents = fileContents.replaceAll(toReplace, toBeReplaced);
        FileWriter writer = new FileWriter(filePath);
        writer.append(fileContents);
        writer.flush();
    }
}
