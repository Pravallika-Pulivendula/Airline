package com.everest.airline.controller;

import com.everest.airline.enums.ClassType;
import com.everest.airline.model.Flight;
import com.everest.airline.service.pricingrule.PricingBasedOnDay;
import com.everest.airline.service.pricingrule.PricingBasedOnSeat;
import com.everest.airline.utils.DatabaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {
    @Autowired
    @Qualifier(value = "Seats")
    private PricingBasedOnSeat pricingBasedOnSeat;
    @Autowired
    @Qualifier(value = "Days")
    private PricingBasedOnDay pricingBasedOnDay;
    @Autowired
    private DatabaseUtil databaseUtil;

    @PostMapping(value = "/book")
    public String bookFlight(int noOfPassengers, long number, String classType, Model model) {
        Flight flight = databaseUtil.getFlight(number);
        double pricePerSeat = flight.getPricePerSeat(classType, pricingBasedOnSeat, pricingBasedOnDay);
        flight.reserveSeats(noOfPassengers, ClassType.valueOf(classType));
        flight = databaseUtil.updateFlight(number, flight);
        model.addAttribute("flights", flight);
        model.addAttribute("noOfPassengers", noOfPassengers);
        model.addAttribute("classType", classType);
        model.addAttribute("pricePerSeat", pricePerSeat);
        return "book";
    }
}
