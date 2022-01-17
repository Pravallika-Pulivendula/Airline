package com.everest.airline.service;

import com.everest.airline.model.Flight;
import com.everest.airline.utils.FileHandler;
import com.everest.airline.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

@Component
public class FlightService {
    @Value("${FILEPATH}")
    private String filePath;
    @Autowired
    Validator validator;
    @Autowired
    FileHandler fileHandler;

    public long getNextFlightNumber() throws FileNotFoundException {
        File directory = new File(filePath);
        File[] files = directory.listFiles();
        if (files == null) throw new FileNotFoundException("No files found");
        if (files.length == 0) return 1001;
        Arrays.sort(files);
        return Long.parseLong(files[files.length - 1].getName()) + 1;
    }

    public long addNewFlight(Flight flight) throws IOException {
        if (validator.isStringValid(flight.getSource()) || validator.isStringValid(flight.getDestination()) || validator.areStringsEqual(flight.getSource(), flight.getDestination()))
            throw new IllegalArgumentException("Arguments are invalid");
        long number = getNextFlightNumber();
        flight.setNumber(number);
        String fileSeparator = "/";
        String path = filePath + fileSeparator + number;
        File newFile = new File(path);
        try {
            if (!newFile.createNewFile()) throw new IOException("File not created");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        fileHandler.writeData(number, flight.toString());
        return number;
    }

    public void updateFlight(long number,Flight newFlight) throws IOException {
        newFlight.setNumber(number);
        fileHandler.writeData(number, newFlight.toString());
    }

    public void deleteFlight(long number)
    {
        
    }
}
