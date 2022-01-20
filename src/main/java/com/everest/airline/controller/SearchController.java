package com.everest.airline.controller;

import com.everest.airline.model.Flight;
import com.everest.airline.service.PricingBasedOnDay;
import com.everest.airline.service.PricingBasedOnSeat;
import com.everest.airline.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


@Controller
public class SearchController {
    @Autowired
    SearchService searchService;
    @Autowired
    PricingBasedOnSeat pricingBasedOnSeat;
    @Autowired
    PricingBasedOnDay pricingBasedOnDay;

    @GetMapping(value = "/")
    public String home(Model model) {
        LocalDate date = LocalDate.now();
        model.addAttribute("date", date);
        return "home";
    }

    @PostMapping(value = "/search")
    public String search(String from, String to, String departureDate, int noOfPassengers, String classType, Model model) throws IOException {
        ArrayList<Flight> flights = (ArrayList<Flight>) searchService.searchFlights(from, to, departureDate, classType, noOfPassengers);
        if (flights.isEmpty()) return "noflights";
        model.addAttribute("flights", flights);
        model.addAttribute("noOfPassengers", noOfPassengers);
        model.addAttribute("classType", classType);
        model.addAttribute("pricingBasedOnSeats", pricingBasedOnSeat);
        model.addAttribute("pricingBasedOnDays", pricingBasedOnDay);
        return "search";
    }
}
