package com.everest.airline;

import com.everest.airline.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class FileHandler {
    File directory = new File("src/main/java/com/everest/airline/flights");
    File[] files = directory.listFiles();
    List<Flight> flightData = new ArrayList<>();
    @Autowired
    ValidateInput validateInput;

    public void writeDataToFile(long number, String fileContents) {
        File[] files = directory.listFiles((File pathname) -> pathname.getName().equals(String.valueOf(number)));
        try {
            if (files == null) throw new FileNotFoundException("File not found");
            Path file = Paths.get(files[0].getPath());
            Files.writeString(file, fileContents, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public Flight getDataFromFile(long number) {
        List<Flight> flight = flightData.stream().filter(f -> f.getNumber() == number).collect(Collectors.toList());
        return flight.get(0);
    }

    public ArrayList<Flight> readDataFromAllFiles(){
        ArrayList<Flight> flights = new ArrayList<>();
        String[] flightDetails;
        try {
            if (files == null) throw new FileNotFoundException("File not found");
            Arrays.sort(files);
            for (File eachFile : files) {
                flightDetails = new Scanner(new File(eachFile.getPath())).useDelimiter("\\Z").next().split(",");
                flights.add(new Flight(Long.parseLong(flightDetails[0]), flightDetails[1], flightDetails[2], LocalDate.parse(flightDetails[3]), LocalTime.parse(flightDetails[4]), LocalTime.parse(flightDetails[5]), Integer.parseInt(flightDetails[6]), Integer.parseInt(flightDetails[7]), Integer.parseInt(flightDetails[8]), Integer.parseInt(flightDetails[9]), Double.parseDouble(flightDetails[10]), Double.parseDouble(flightDetails[11]), Double.parseDouble(flightDetails[12])));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return flights;
    }

    public List<Flight> filterData(String from, String to, String departureDate, int noOfPassengers)  {
        flightData = readDataFromAllFiles().stream().filter(flight -> flight.getSource().equals(from) && flight.getDestination().equals(to) && flight.getDepartureDate().toString().equals(departureDate) && flight.getTotalSeats() > noOfPassengers).collect(Collectors.toList());
        return flightData;
    }
}