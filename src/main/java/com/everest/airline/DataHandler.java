package com.everest.airline;

import com.everest.airline.model.Flight;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class DataHandler {
    File directory = new File("src/main/java/com/everest/airline/flights");
    List<Flight> flightData = new ArrayList<>();

    public void writeDataToFile(long number, String toReplace, String toBeReplaced) throws IOException {
        File[] files = directory.listFiles((File pathname) -> pathname.getName().equals(String.valueOf(number)));
        if (files == null) throw new FileNotFoundException("File not found");
        File file = new File(files[0].getPath());
        String content = Files.readString(Path.of(file.getPath()), Charset.defaultCharset());
        content = content.replaceAll(toReplace, toBeReplaced);
        Files.write(Path.of(file.getPath()), content.getBytes(StandardCharsets.UTF_8));
    }

    public Flight getData(long number) {
        List<Flight> flight = flightData.stream().filter(f -> f.getNumber() == number).collect(Collectors.toList());
        return flight.get(0);
    }

    public Flight getDataFromFile(long number) throws IOException {
        File[] files = directory.listFiles((File pathname) -> pathname.getName().equals(String.valueOf(number)));
        if (files == null) throw new FileNotFoundException("File not found");
        File file = new File(files[0].getPath());
        String content = Files.readString(Path.of(file.getPath()), Charset.defaultCharset());
        String[] flightDetails = content.split(",");
        return new Flight(Long.parseLong(flightDetails[0]), flightDetails[1], flightDetails[2], LocalDate.parse(flightDetails[3]), LocalTime.parse(flightDetails[4]), LocalTime.parse(flightDetails[5]), Integer.parseInt(flightDetails[6]), Integer.parseInt(flightDetails[7]), Integer.parseInt(flightDetails[8]), Integer.parseInt(flightDetails[9]), Double.parseDouble(flightDetails[10]), Double.parseDouble(flightDetails[11]), Double.parseDouble(flightDetails[12]));
    }

    public ArrayList<Flight> readDataFromAllFiles() throws IOException {
        ArrayList<Flight> flights = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files == null) throw new FileNotFoundException("File not found");
        Arrays.sort(files);
        for (File eachFile : files)
            flights.add(getDataFromFile(Long.parseLong(eachFile.getName())));
        return flights;
    }

    public List<Flight> filterData(String from, String to, String departureDate, int noOfPassengers, File[] directoryListing) throws FileNotFoundException {
        String[] flightDetails;
        for (File eachFile : directoryListing) {
            flightDetails = new Scanner(new File(eachFile.getPath())).useDelimiter("\\Z").next().split(",");
            if (flightDetails[1].equals(from) && flightDetails[2].equals(to) && flightDetails[3].equals(departureDate) && Integer.parseInt(flightDetails[6]) >= noOfPassengers)
                flightData.add(new Flight(Long.parseLong(flightDetails[0]), flightDetails[1], flightDetails[2], LocalDate.parse(flightDetails[3]), LocalTime.parse(flightDetails[4]), LocalTime.parse(flightDetails[5]), Integer.parseInt(flightDetails[6]), Integer.parseInt(flightDetails[7]), Integer.parseInt(flightDetails[8]), Integer.parseInt(flightDetails[9]), Double.parseDouble(flightDetails[10]), Double.parseDouble(flightDetails[11]), Double.parseDouble(flightDetails[12])));
        }
        return flightData;
    }
}