package com.everest.airline.controller;

import com.everest.airline.exception.FlightsNotFoundException;
import com.everest.airline.model.Flight;
import com.everest.airline.service.SearchService;
import com.everest.airline.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class SearchController {
    @Autowired
    SearchService searchService;
    @Autowired
    SeatService seatService;
    private List<Flight> flights;
    private final ArrayList<String> classType = new ArrayList<>(Arrays.asList("Economic","First","Second"));


    @RequestMapping(value = "/")
    public String home(Model model) {
        LocalDate date = LocalDate.now();
        model.addAttribute("date", date);
        return "home";
    }


    @RequestMapping(value = "/search")
    public String search(String from, String to, String departureDate, Model model) throws IOException {
        flights = searchService.searchFlights(from, to, departureDate);
        if (flights.size() == 0) return "redirect:/no-flights-found";
        model.addAttribute("flights", flights);
        model.addAttribute("classType", classType);
        return "search";
    }

    @RequestMapping(value = "/{number}")
    public String book(@PathVariable("number") long number,int noOfPassengers,String classType) throws IOException {
        flights = flights.stream().filter(flight -> flight.getAvailableSeats() != 0).collect(Collectors.toList());
        if (flights.size() == 0) return "redirect:/no-flights-found";
        seatService.updateAvailableSeats(number,noOfPassengers,flights);
        return "redirect:/book";
    }

    @RequestMapping(value = "/book")
    public String bookTicket(Model model) {
        flights = flights.stream().filter(flight -> flight.getAvailableSeats() != 0).collect(Collectors.toList());
        if (flights.size() == 0) return "redirect:/no-flights-found";
        model.addAttribute("flights", flights);
        model.addAttribute("classType", classType);
        return "search";
    }
}
