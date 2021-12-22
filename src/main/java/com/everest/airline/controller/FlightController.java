package com.everest.airline.controller;


import com.everest.airline.Data;
import com.everest.airline.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class FlightController {
    @Autowired
    Data data;

    String filePath = "/Users/Pravallika/Documents/Assignments/airlines/src/main/java/com/everest/airline/flights";
    File directory = new File(filePath);

    @GetMapping("/flights")
    public ArrayList<Flight> getAllFlights() throws IOException {
        return data.readDataFromAllFiles();
    }

    @GetMapping("/flights/{number}")
    public Flight getFlight(@PathVariable("number") long number) throws IOException {
        return data.getDataFromFile(number);
    }

    @DeleteMapping("/flights/{number}")
    public boolean deleteFlight(@PathVariable("number") long number) {
        File[] files = directory.listFiles((File pathname) -> pathname.getName().equals(String.valueOf(number)));
        assert files != null;
        File file = new File(files[0].getPath());
        return file.delete();
    }
}
