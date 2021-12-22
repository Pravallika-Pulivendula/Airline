package com.everest.airline.controller;

import com.everest.airline.Data;
import com.everest.airline.model.Flight;
import com.everest.airline.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class BookingController {
    @Autowired
    SeatService seatService;

    @RequestMapping(value = "/{number}/{noOfPassengers}/{classType}/{pricePerSeat}")
    public String book(@PathVariable("number") long number, @PathVariable("noOfPassengers") int noOfPassengers, @PathVariable("classType") String classType, @PathVariable("pricePerSeat") double pricePerSeat, @ModelAttribute Flight flight, BindingResult bindingResult, Model model) throws IOException {
        System.out.println(flight.getNumber());
        System.out.println(flight.getDestination());
        System.out.println(flight.getSource());
        System.out.println(flight.getAvailableSeats());
        System.out.println(flight.getArrivalTime());
        System.out.println(flight);
        seatService.calculatePriceBasedOnDate(flight);
        seatService.updateAvailableSeats(number, noOfPassengers, classType, flight);
        return "redirect:/book/{number}/{noOfPassengers}/{classType}/{pricePerSeat}";
    }

    @RequestMapping(value = "/book/{number}/{noOfPassengers}/{classType}/{pricePerSeat}")
    public String bookTicket(@PathVariable("number") long number, @PathVariable("noOfPassengers") int noOfPassengers, @PathVariable("classType") String classType, @PathVariable("pricePerSeat") double pricePerSeat, Model model) throws IOException {
        Flight flight = Data.getFlightDataFromFile(number);
        flight.setPricePerSeat(pricePerSeat);
        model.addAttribute("flights", flight);
        model.addAttribute("noOfPassengers", noOfPassengers);
        model.addAttribute("classType", classType);
        return "book";
    }
}
