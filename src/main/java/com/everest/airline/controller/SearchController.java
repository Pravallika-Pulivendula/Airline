package com.everest.airline.controller;

import com.everest.airline.Data;
import com.everest.airline.model.Flight;
import com.everest.airline.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;


@Controller
public class SearchController {
    SearchService searchService = new SearchService();
    @RequestMapping(value = "/")
    public String home(Model model) {
        LocalDate date = LocalDate.now();
        model.addAttribute("date", date);
        return "home";
    }

    @RequestMapping(value = "/search")
    public String search(String from,String to, String departureDate,Model model){
        List<Flight> flights =  searchService.searchDepartureDate(from,to,departureDate, Data.getFlights());
        model.addAttribute("flights",flights);
        return "search";
    }

}
