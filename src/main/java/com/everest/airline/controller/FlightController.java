package com.everest.airline.controller;


import com.everest.airline.utils.FileHandler;
import com.everest.airline.model.Flight;
import com.everest.airline.service.FlightService;
import com.everest.airline.utils.ValidateInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
public class FlightController {
    @Autowired
    FileHandler data;
    @Autowired
    FlightService flightService;
    @Autowired
    ValidateInput validateInput;

    private static final String FILE_PATH = "src/main/java/com/everest/airline/flights";

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
        if(validateInput.isStringValid(flight.getSource()) || validateInput.isStringValid(flight.getDestination()) || validateInput.areStringsEqual(flight.getSource(), flight.getDestination()))
            throw new IllegalArgumentException("Arguments are invalid");
        long number = flightService.getNextFlightNumber();
        flight.setNumber(number);
        String fileSeparator = "/";
        String path = FILE_PATH + fileSeparator + number;
        File newFile = new File(path);
        try {if (!newFile.createNewFile()) throw new IOException("File not created");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        data.writeDataToFile(number, flight.toString());
        return number;
    }

    @PutMapping("/flights/{number}")
    public Flight updateFlight(@PathVariable("number") long number, @RequestBody Flight newFlight) throws IOException {
        newFlight.setNumber(number);
        data.writeDataToFile(number, newFlight.toString());
        return newFlight;
    }

    @DeleteMapping("/flights/{number}")
    public void deleteFlight(@PathVariable("number") long number) throws IOException {
        File directory = new File(FILE_PATH);
        File[] files = directory.listFiles((File pathname) -> pathname.getName().equals(String.valueOf(number)));
        if(files == null) throw new FileNotFoundException("No such files");
        Files.delete(Path.of(files[0].getPath()));
    }
}
