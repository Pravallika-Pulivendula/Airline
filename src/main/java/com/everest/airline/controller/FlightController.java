package com.everest.airline.controller;


import com.everest.airline.FileHandler;
import com.everest.airline.ValidateInput;
import com.everest.airline.model.Flight;
import com.everest.airline.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
public class FlightController {
    @Autowired
    FileHandler data;
    @Autowired
    FlightService flightService;
    @Autowired
    ValidateInput validateInput;

    String filePath = "src/main/java/com/everest/airline/flights";
    File directory = new File(filePath);

    @GetMapping("/flights")
    public ArrayList<Flight> getAllFlights() {
        return data.readDataFromAllFiles();
    }

    @GetMapping("/flights/{number}")
    public Flight getFlight(@PathVariable("number") long number) {
        return data.readDataFromAllFiles().stream().filter(flight -> flight.getNumber() == number).collect(Collectors.toList()).get(0);
    }

    @PostMapping("/flights")
    public void addFlight(String source, String destination, String departureDate, String departTime, String arrivalTime, int availableSeats, int firstClassSeats, int secondClassSeats, int economicClassSeats, double firstClassBasePrice, double secondClassBasePrice, double economicClassBasePrice) {
        if (validateInput.isStringValid(source) || validateInput.isStringValid(destination) || validateInput.areStringsEqual(source, destination))
            throw new IllegalArgumentException("Arguments are not valid");
        long number = flightService.getNextFlightNumber();
        String path = filePath + "/" + number;
        File newFile = new File(path);
        try {
            newFile.createNewFile();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        String fileContents = flightService.toString(number, source, destination, departureDate, departTime, arrivalTime, availableSeats, firstClassSeats, secondClassSeats, economicClassSeats, firstClassBasePrice, secondClassBasePrice, economicClassBasePrice);
        data.writeDataToFile(number, fileContents);
    }

    @PutMapping("/flights/{number}")
    public Flight updateFlight(@PathVariable("number") long number, String source, String destination, String departureDate, String departTime, String arrivalTime, int availableSeats, int firstClassSeats, int secondClassSeats, int economicClassSeats, double firstClassBasePrice, double secondClassBasePrice, double economicClassBasePrice) {
        if (validateInput.isStringValid(source) || validateInput.isStringValid(destination) || validateInput.areStringsEqual(source, destination))
            throw new IllegalArgumentException("Arguments are not valid");
        Flight newFlight = new Flight(number, source, destination, validateInput.parseInputDate(departureDate), validateInput.parseInputTime(departTime), validateInput.parseInputTime(arrivalTime), availableSeats, firstClassSeats, secondClassSeats, economicClassSeats, firstClassBasePrice, secondClassBasePrice, economicClassBasePrice);
        String newFlightDetails = newFlight.toString();
        data.writeDataToFile(number, newFlightDetails);
        return newFlight;
    }

    @DeleteMapping("/flights/{number}")
    public void deleteFlight(@PathVariable("number") long number) {
        File[] files = directory.listFiles((File pathname) -> pathname.getName().equals(String.valueOf(number)));
        try {
            if (files == null) throw new FileNotFoundException("No such file");
            File file = new File(files[0].getPath());
            file.delete();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
}
