package com.everest.airline.controller;

import com.everest.airline.model.Flight;
import com.everest.airline.service.PricingService;
import com.everest.airline.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


@Controller
public class SearchController {
    @Autowired
    SearchService searchService;

    @RequestMapping(value = "/")
    public String home(Model model) {
        LocalDate date = LocalDate.now();
        model.addAttribute("date", date);
        return "home";
    }

    @RequestMapping(value = "/search")
    public String search(String from, String to, String departureDate, int noOfPassengers, String classType, Model model) throws IOException {
        ArrayList<Flight> flights = (ArrayList<Flight>) searchService.searchFlights(from, to, departureDate, classType, noOfPassengers);
        if (flights.isEmpty()) return "redirect:/no-flights-found";
        flights.forEach(flight -> flight.updatePricePerSeat(classType));
        model.addAttribute("flights", flights);
        model.addAttribute("noOfPassengers", noOfPassengers);
        model.addAttribute("classType", classType);
        return "search";
    }

}
