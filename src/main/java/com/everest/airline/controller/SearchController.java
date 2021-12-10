package com.everest.airline.controller;

import com.everest.airline.exception.FlightsNotFoundException;
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

    @RequestMapping(value = "/")
    public String home(Model model) {
        LocalDate date = LocalDate.now();
        model.addAttribute("date", date);
        return "home";
    }

    @RequestMapping(value = "/search")
    public String search(String from, String to, String departureDate, Model model) throws IOException {
        flights = searchService.searchFlights(from, to, departureDate);
        System.out.println(flights);
        if (flights.size() == 0) throw new FlightsNotFoundException("No flights found");
        model.addAttribute("flights", flights);
        return "search";
    }

    @RequestMapping(value = "/{number}")
    public String book(@PathVariable("number") long number, Model model) throws IOException {
        if(flights == null) throw new FlightsNotFoundException("No flights found");
        seatService.updateAvailableSeats(number, flights.stream().filter(flight -> flight.getAvailableSeats() != 0).collect(Collectors.toList()));
        return "redirect:/book";
    }

    @RequestMapping(value = "/book")
    public String bookTicket(Model model) {
        if(flights == null) throw new FlightsNotFoundException("No flights found");
        model.addAttribute("flights", flights.stream().filter(flight -> flight.getAvailableSeats() != 0).collect(Collectors.toList()));
        return "search";
    }
}
