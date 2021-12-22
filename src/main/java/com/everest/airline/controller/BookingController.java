package com.everest.airline.controller;

import com.everest.airline.Data;
import com.everest.airline.model.Flight;
import com.everest.airline.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class BookingController {
    @Autowired
    SeatService seatService;
    @Autowired
    Data data;

    @RequestMapping(value = "/{number}/{noOfPassengers}/{classType}/{pricePerSeat}")
    public String book(@PathVariable("number") long number, @PathVariable("noOfPassengers") int noOfPassengers, @PathVariable("classType") String classType, @PathVariable("pricePerSeat") double pricePerSeat, Model model) throws IOException {
        Flight flight = data.getDataFromFile(number);
        flight.extraChargeBasedOnDate(seatService);
        seatService.updateAvailableSeats(number, noOfPassengers, classType,flight);
        return "redirect:/book/{number}/{noOfPassengers}/{classType}/{pricePerSeat}";
    }

    @RequestMapping(value = "/book/{number}/{noOfPassengers}/{classType}/{pricePerSeat}")
    public String bookTicket(@PathVariable("number") long number, @PathVariable("noOfPassengers") int noOfPassengers, @PathVariable("classType") String classType, @PathVariable("pricePerSeat") double pricePerSeat, Model model) throws IOException {
        Flight flight = data.getDataFromFile(number);
        flight.setPricePerSeat(pricePerSeat);
        model.addAttribute("flights", flight);
        model.addAttribute("noOfPassengers", noOfPassengers);
        model.addAttribute("classType", classType);
        return "book";
    }
}
