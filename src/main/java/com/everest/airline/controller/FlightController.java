package com.everest.airline.controller;


import com.everest.airline.model.Flight;
import com.everest.airline.service.FlightService;
import com.everest.airline.utils.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
public class FlightController {
    @Autowired
    FileHandler fileHandler;
    @Autowired
    FlightService flightService;

    @GetMapping("/flights")
    public List<Flight> getAllFlights() throws IOException {
        return fileHandler.readAllData();
    }

    @GetMapping("/flights/{number}")
    public Flight getFlight(@PathVariable("number") long number) throws FileNotFoundException {
        return fileHandler.readData(number);
    }

    @PostMapping("/flights")
    public long addFlight(@RequestBody Flight flight) throws IOException {
        return flightService.addNewFlight(flight);
    }

    @PutMapping("/flights/{number}")
    public Flight updateFlight(@PathVariable("number") long number, @RequestBody Flight newFlight) throws IOException {
        flightService.updateFlight(number, newFlight);
        return newFlight;
    }

    @DeleteMapping("/flights/{number}")
    public void deleteFlight(@PathVariable("number") long number) throws IOException {
        flightService.deleteFlight(number);
    }

}
