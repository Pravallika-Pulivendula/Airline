package com.everest.airline.controller;

import com.everest.airline.enums.ClassType;
import com.everest.airline.model.Flight;
import com.everest.airline.utils.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class BookController {
    @Autowired
    FileHandler fileHandler;

    @PostMapping(value = "/book")
    public String bookFlight(int noOfPassengers, long number, String classType, Model model) throws IOException {
        Flight flight = fileHandler.getData(number);
        double pricePerSeat = flight.getPricePerSeat(classType);
        flight.reserveSeats(noOfPassengers, ClassType.valueOf(classType));
        fileHandler.writeData(number, flight.toString());
        model.addAttribute("flights", flight);
        model.addAttribute("noOfPassengers", noOfPassengers);
        model.addAttribute("classType", classType);
        model.addAttribute("pricePerSeat", pricePerSeat);
        return "book";
    }
}
