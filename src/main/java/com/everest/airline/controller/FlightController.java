package com.everest.airline.controller;


import com.everest.airline.FileHandler;
import com.everest.airline.model.Flight;
import com.everest.airline.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
public class FlightController {
    @Autowired
    FileHandler data;
    @Autowired
    FlightService flightService;

    private static final String FILEPATH = "src/main/java/com/everest/airline/flights";

    @GetMapping("/flights")
    public List<Flight> getAllFlights() throws IOException {
        return data.readDataFromAllFiles();
    }

    @GetMapping("/flights/{number}")
    public Flight getFlight(@PathVariable("number") long number) throws FileNotFoundException {
        return data.readDataFromFile(number);
    }

    @PostMapping("/flights")
    public long addFlight(@RequestBody Flight flight) throws IOException {
        long number = flightService.getNextFlightNumber();
        flight.setNumber(number);
        data.writeDataToFile(number, flight.toString());
        return number;
    }

    @PutMapping("/flights/{number}")
    public Flight updateFlight(@PathVariable("number") long number, @RequestBody Flight newFlight) throws IOException {
        data.writeDataToFile(number, newFlight.toString());
        return newFlight;
    }

    @DeleteMapping("/flights/{number}")
    public void deleteFlight(@PathVariable("number") long number)  {
        File directory = new File(FILEPATH);
        File[] files = directory.listFiles((File pathname) -> pathname.getName().equals(String.valueOf(number)));
        try {
            if (files == null) throw new FileNotFoundException("File not found");
            File file = new File(files[0].getPath());
            if (!file.delete()) throw new IOException("File is not deleted");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
