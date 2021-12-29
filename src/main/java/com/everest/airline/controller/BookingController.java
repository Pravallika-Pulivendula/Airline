package com.everest.airline.controller;

import com.everest.airline.FileHandler;
import com.everest.airline.enums.ClassType;
import com.everest.airline.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class BookingController {
    @Autowired
    FileHandler data;

    @RequestMapping(value = "/{number}/{noOfPassengers}/{classType}/{pricePerSeat}")
    public String book(@PathVariable("number") long number, @PathVariable("noOfPassengers") int noOfPassengers, @PathVariable("classType") String classType, @PathVariable("pricePerSeat") double pricePerSeat) throws IOException {
        Flight flight = data.getDataFromFile(number);
        flight.updateSeats(noOfPassengers, ClassType.valueOf(classType));
        data.writeDataToFile(number, flight.toString());
        flight.updatePricePerSeat(classType);
        return "redirect:/book/{number}/{noOfPassengers}/{classType}/{pricePerSeat}";
    }

    @RequestMapping(value = "/book/{number}/{noOfPassengers}/{classType}/{pricePerSeat}")
    public String bookTicket(@PathVariable("number") long number, @PathVariable("noOfPassengers") int noOfPassengers, @PathVariable("classType") String classType, @PathVariable("pricePerSeat") double pricePerSeat, Model model) {
        Flight flight = data.getDataFromFile(number);
        flight.setPricePerSeat(pricePerSeat);
        model.addAttribute("flights", flight);
        model.addAttribute("noOfPassengers", noOfPassengers);
        model.addAttribute("classType", classType);
        return "book";
    }
}
