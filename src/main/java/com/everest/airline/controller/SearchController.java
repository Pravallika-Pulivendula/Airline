package com.everest.airline.controller;

import com.everest.airline.model.Flight;
import com.everest.airline.service.SearchService;
import com.everest.airline.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class SearchController {
    @Autowired
    SearchService searchService;
    @Autowired
    SeatService seatService;
    private List<Flight> flights;
    private int noOfPassengers;
    private String classType;
    private double totalFair = 0;


    @RequestMapping(value = "/")
    public String home(Model model) {
        LocalDate date = LocalDate.now();
        model.addAttribute("date", date);
        return "home";
    }


    @RequestMapping(value = "/search")
    public String search(String from, String to, String departureDate, int noOfPassengers, String classType, Model model) throws IOException {
        this.noOfPassengers = noOfPassengers;
        this.classType = classType;
        flights = searchService.searchFlights(from, to, departureDate,this.classType,this.noOfPassengers);
        if (flights.size() == 0) return "redirect:/no-flights-found";
        model.addAttribute("flights", flights);
        model.addAttribute("classType",this.classType);
        return "search";
    }

    @RequestMapping(value = "/{number}")
    public String book(@PathVariable("number") long number,Model model) throws IOException {
        flights = flights.stream().filter(flight -> flight.getAvailableSeats() != 0 && flight.getNumber() == number).collect(Collectors.toList());
        if (flights.size() == 0) return "redirect:/no-flights-found";
        totalFair = seatService.updateAvailableSeats(number,noOfPassengers,classType,flights);
        return "redirect:/book";
    }

    @RequestMapping(value = "/book")
    public String bookTicket(Model model) {
        flights = flights.stream().filter(flight -> flight.getAvailableSeats() != 0 ).collect(Collectors.toList());
        if (flights.size() == 0) return "redirect:/no-flights-found";
        model.addAttribute("flights", flights);
        model.addAttribute("noOfPassengers",noOfPassengers);
        model.addAttribute("classType",classType);
        model.addAttribute("totalFair",totalFair);
        return "book";
    }
}
