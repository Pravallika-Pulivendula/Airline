package com.everest.airline;

import com.everest.airline.model.Flight;
import com.everest.airline.model.FlightSeatType;
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
    private static final String FILEPATH = "src/main/java/com/everest/airline/flights";
    private static final String ERROR_MESSAGE = "File not found";
    List<Flight> flightData = new ArrayList<>();

    public void writeDataToFile(long number, String fileContents) {
        File directory = new File(FILEPATH);
        File[] allFiles = directory.listFiles((File pathname) -> pathname.getName().equals(String.valueOf(number)));
        try {
            if (allFiles == null) throw new FileNotFoundException(ERROR_MESSAGE);
            Path file = Paths.get(allFiles[0].getPath());
            Files.writeString(file, fileContents, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public Flight readDataFromFile(long number) throws FileNotFoundException {
        File directory = new File(FILEPATH);
        File[] allFiles = directory.listFiles((File pathname) -> pathname.getName().equals(String.valueOf(number)));
        if (allFiles == null) throw new FileNotFoundException(ERROR_MESSAGE);
        File file = new File(allFiles[0].getPath());
        String[] flightDetails;
        try (Scanner scanner = new Scanner(file)) {
            flightDetails = scanner.useDelimiter("\\Z").next().split(",");
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException(ERROR_MESSAGE);
        }
        return new Flight(Long.parseLong(flightDetails[0]), flightDetails[1], flightDetails[2], LocalDate.parse(flightDetails[3]), LocalTime.parse(flightDetails[4]), LocalTime.parse(flightDetails[5]), Integer.parseInt(flightDetails[6]), new FlightSeatType(50, Integer.parseInt(flightDetails[7]), Double.parseDouble(flightDetails[8])), new FlightSeatType(10, Integer.parseInt(flightDetails[9]), Double.parseDouble(flightDetails[10])), new FlightSeatType(30, Integer.parseInt(flightDetails[11]), Double.parseDouble(flightDetails[12])));
    }

    public Flight getDataFromFile(long number) {
        List<Flight> flight = flightData.stream().filter(f -> f.getNumber() == number).collect(Collectors.toList());
        return flight.get(0);
    }

    public List<Flight> readDataFromAllFiles() throws IOException {
        File directory = new File(FILEPATH);
        File[] files = directory.listFiles();
        ArrayList<Flight> flights = new ArrayList<>();
        String[] flightDetails;
        if (files == null) throw new FileNotFoundException(ERROR_MESSAGE);
        for (File eachFile : files) {
            try (Scanner scanner = new Scanner(new File(eachFile.getPath()))) {
                flightDetails = scanner.useDelimiter("\\Z").next().split(",");
                flights.add(new Flight(Long.parseLong(flightDetails[0]), flightDetails[1], flightDetails[2], LocalDate.parse(flightDetails[3]), LocalTime.parse(flightDetails[4]), LocalTime.parse(flightDetails[5]), Integer.parseInt(flightDetails[6]), new FlightSeatType(50, Integer.parseInt(flightDetails[7]), Double.parseDouble(flightDetails[8])), new FlightSeatType(10, Integer.parseInt(flightDetails[9]), Double.parseDouble(flightDetails[10])), new FlightSeatType(30, Integer.parseInt(flightDetails[11]), Double.parseDouble(flightDetails[12]))));
            } catch (IOException ioException) {
                throw new IOException("Cannot read from files");
            }
        }
        Arrays.sort(files);
        return flights;
    }

    public List<Flight> filterData(String from, String to, String departureDate, int noOfPassengers) throws IOException {
        flightData = readDataFromAllFiles().stream().filter(flight -> flight.getSource().equals(from) && flight.getDestination().equals(to) && flight.getDepartureDate().toString().equals(departureDate) && flight.getTotalSeats() > noOfPassengers).collect(Collectors.toList());
        return flightData;
    }
}