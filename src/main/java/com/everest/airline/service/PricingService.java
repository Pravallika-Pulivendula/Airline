package com.everest.airline.service;

import com.everest.airline.model.Flight;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PricingService {

    public double calculateChargeBasedOnSeatType(double basePrice, double totalSeats, int noOfSeats) {
        if (noOfSeats > totalSeats - (totalSeats * 30) / 100 && noOfSeats < totalSeats)
            return 0;
        if (noOfSeats > totalSeats - (totalSeats * 50) / 100 && noOfSeats < totalSeats - (totalSeats * 30) / 100)
            return (basePrice * 20) / 100;
        if (noOfSeats > totalSeats - (totalSeats * 75) / 100 && noOfSeats < totalSeats - (totalSeats * 50) / 100)
            return (basePrice * 35) / 100;
        if (noOfSeats < totalSeats - (totalSeats * 75) / 100 && noOfSeats > 0)
            return (basePrice * 50) / 100;
        return 0;
    }

    public double calculateChargeBasedOnDays(long days) {
        if (days > 10 && days <= 15) return 0;
        if (days < 10 && days > 3) return (double) (days * 2) / 100;
        if (days < 3 && days > 0) return (double) (days * 10) / 100;
        return 0;
    }

    public void setPricePerSeatForEachFlight(ArrayList<Flight> flights, String classType) {
        flights.forEach(flight -> flight.updatePricePerSeat(classType));
    }
}