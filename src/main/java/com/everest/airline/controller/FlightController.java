package com.everest.airline.controller;


import com.everest.airline.DataHandler;
import com.everest.airline.model.Flight;
import com.everest.airline.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class FlightController {
    @Autowired
    DataHandler data;
    @Autowired
    FlightService flightService;

    String filePath = "src/main/java/com/everest/airline/flights";
    File directory = new File(filePath);
    File[] files = directory.listFiles();

    @GetMapping("/flights")
    public ArrayList<Flight> getAllFlights() throws IOException {
        return data.readDataFromAllFiles();
    }

    @GetMapping("/flights/{number}")
    public Flight getFlight(@PathVariable("number") long number) throws IOException {
        return data.getDataFromFile(number);
    }

    @PostMapping("/flights")
    public long addFlight(String source, String destination, String departureDate, String departTime, String arrivalTime, int availableSeats, int firstClassSeats, int secondClassSeats, int economicClassSeats, double firstClassBasePrice, double secondClassBasePrice, double economicClassBasePrice) throws IOException {
        return flightService.getNextFlightNumber();
    }

    @DeleteMapping("/flights/{number}")
    public boolean deleteFlight(@PathVariable("number") long number) throws FileNotFoundException {
        File[] files = directory.listFiles((File pathname) -> pathname.getName().equals(String.valueOf(number)));
        if (files == null) throw new FileNotFoundException("No such file");
        File file = new File(files[0].getPath());
        return file.delete();
    }
}
