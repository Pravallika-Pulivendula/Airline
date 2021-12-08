package com.everest.airline.service;

import com.everest.airline.model.Flight;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SeatService {
    String filePath = "/Users/Pravallika/Documents/Assignments/airlines/src/main/java/com/everest/airline/data.txt";

    public void updateAvailableSeats(long number, List<Flight> flightsData) throws IOException {
        Flight flights = flightsData.stream().filter(flight -> flight.getNumber() == number)
                .collect(Collectors.toList()).get(0);
        String toReplace = flights.getNumber() + "," + flights.getSource() + "," + flights.getDestination() + "," + flights.getDepartureDate() + "," + flights.getDepartTime() + "," + flights.getArrivalTime() + "," + flights.getAvailableSeats();
        flights.updateAvailableSeats();
        String toBeReplaced = flights.getNumber() + "," + flights.getSource() + "," + flights.getDestination() + "," + flights.getDepartureDate() + "," + flights.getDepartTime() + "," + flights.getArrivalTime() + "," + flights.getAvailableSeats();
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
