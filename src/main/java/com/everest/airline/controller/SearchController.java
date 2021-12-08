package com.everest.airline.controller;

import com.everest.airline.model.Flight;
import com.everest.airline.service.SearchService;
import com.everest.airline.service.SeatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@Controller
public class SearchController {
    SearchService searchService = new SearchService();
    SeatService seatService = new SeatService();
    @RequestMapping(value = "/")
    public String home(Model model) {
        LocalDate date = LocalDate.now();
        model.addAttribute("date", date);
        return "home";
    }

    @RequestMapping(value = "/search")
    public String search(String from,String to, String departureDate,Model model) throws IOException {
        List<Flight> flights =  searchService.searchDepartureDate(from,to,departureDate);
        System.out.println(flights);
        model.addAttribute("flights",flights);
        return "search";
    }

    @RequestMapping(value = "/{number}")
    public String book(@PathVariable("number") long number,Model model) throws IOException {
        seatService.updateAvailableSeats(number,searchService.getFlightData());
        return "redirect:/book";
    }

    @RequestMapping(value = "/book")
    public String bookTicket(Model model){
        List<Flight> flights = searchService.getFlightData();
        model.addAttribute("flights",flights);
        return "search";
    }
}
