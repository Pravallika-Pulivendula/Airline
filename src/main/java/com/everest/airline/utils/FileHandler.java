package com.everest.airline.utils;

import com.everest.airline.model.Flight;
import com.everest.airline.model.FlightSeatType;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FileHandler {
    private static final String FILEPATH = "src/main/java/com/everest/airline/flights";
    private static final String ERROR_MESSAGE = "File not found";
    List<Flight> flightData = new ArrayList<>();

    public void writeDataToFile(long number, String fileContents) throws IOException {
        File directory = new File(FILEPATH);
        File[] allFiles = directory.listFiles((File pathname) -> pathname.getName().equals(String.valueOf(number)));
        try {
            if (allFiles == null) throw new FileNotFoundException(ERROR_MESSAGE);
            Path file = Paths.get(allFiles[0].getPath());
            Files.writeString(file, fileContents, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IOException("Can't write to file");
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
        return new Flight(flightDetails[1], flightDetails[2], LocalDate.parse(flightDetails[3]), LocalTime.parse(flightDetails[4]), LocalTime.parse(flightDetails[5]), new FlightSeatType(50, Integer.parseInt(flightDetails[7]), Double.parseDouble(flightDetails[8])), new FlightSeatType(10, Integer.parseInt(flightDetails[9]), Double.parseDouble(flightDetails[10])), new FlightSeatType(30, Integer.parseInt(flightDetails[11]), Double.parseDouble(flightDetails[12])));
    }

    public Flight getDataFromFile(long number) {
        List<Flight> flight = flightData.stream().filter(f -> f.getNumber() == number).collect(Collectors.toList());
        return flight.get(0);
    }

    public List<Flight> readDataFromAllFiles() throws IOException {
        List<Flight> flights = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(FILEPATH))) {
            paths.filter(Files::isRegularFile).forEach(filePath ->
            {
                try (Scanner scanner = new Scanner(new FileReader(String.valueOf(filePath)))) {
                    String[] flightDetails = scanner.useDelimiter("\\Z").next().split(",");
                    Flight flight = new Flight(flightDetails[1], flightDetails[2], LocalDate.parse(flightDetails[3]), LocalTime.parse(flightDetails[4]), LocalTime.parse(flightDetails[5]), new FlightSeatType(50, Integer.parseInt(flightDetails[6]), Double.parseDouble(flightDetails[7])), new FlightSeatType(10, Integer.parseInt(flightDetails[8]), Double.parseDouble(flightDetails[9])), new FlightSeatType(30, Integer.parseInt(flightDetails[10]), Double.parseDouble(flightDetails[11])));
                    flight.setNumber(Long.parseLong(flightDetails[0]));
                    flights.add(flight);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
        }
        return flights;
    }

    public List<Flight> filterData(String from, String to, String departureDate) throws IOException {
        flightData = readDataFromAllFiles().stream().filter(flight -> flight.getSource().equals(from) && flight.getDestination().equals(to) && flight.getDepartureDate().toString().equals(departureDate)).collect(Collectors.toList());
        return flightData;
    }
}