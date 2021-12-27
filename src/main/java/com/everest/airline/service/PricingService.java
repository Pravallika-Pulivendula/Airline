package com.everest.airline.service;

import com.everest.airline.model.Flight;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PricingService {

    public double calculateChargeBasedOnSeatType(double basePrice, double totalSeats, int noOfSeats) {
        double extraCharge = 0;
        if (noOfSeats > totalSeats - (totalSeats * 30) / 100 && noOfSeats < totalSeats) extraCharge = 0;
        else if (noOfSeats > totalSeats - (totalSeats * 50) / 100 && noOfSeats < totalSeats - (totalSeats * 30) / 100)
            extraCharge = (basePrice * 20) / 100;
        else if (noOfSeats > totalSeats - (totalSeats * 75) / 100 && noOfSeats < totalSeats - (totalSeats * 50) / 100)
            extraCharge = (basePrice * 35) / 100;
        else if (noOfSeats < totalSeats - (totalSeats * 75) / 100 && noOfSeats > 0)
            extraCharge = (basePrice * 50) / 100;
        return extraCharge;
    }

    public double calculateChargeBasedOnDays(long days) {
        double extraCharge = 0;
        if (days > 10 && days <= 15) extraCharge = 0;
        else if (days < 10 && days > 3) extraCharge = (double) (days * 2) / 100;
        else if (days < 3 && days > 0) extraCharge = (double) (days * 10) / 100;
        return extraCharge;
    }

    public void setPricePerSeatForEachFlight(ArrayList<Flight> flights, String classType) {
        flights.forEach(flight -> flight.updatePricePerSeat(classType));
    }
}