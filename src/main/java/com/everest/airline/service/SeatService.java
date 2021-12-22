package com.everest.airline.service;

import com.everest.airline.Data;
import com.everest.airline.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class SeatService {
    @Autowired
    Data data;

    public void updateAvailableSeats(long number, int noOfPassengers, String classType, Flight flight) throws IOException {
        String toReplace = flight.getAvailableSeats() + "," + flight.getFirstClassSeats() + "," + flight.getSecondClassSeats() + "," + flight.getEconomicClassSeats();
        flight.updateSeats(noOfPassengers, classType);
        String toBeReplaced = flight.getAvailableSeats() + "," + flight.getFirstClassSeats() + "," + flight.getSecondClassSeats() + "," + flight.getEconomicClassSeats();
        data.writeDataToFile(number, toReplace, toBeReplaced);
    }

    public void setPricePerSeatForEachFlight(ArrayList<Flight> flights, String classType) {
        for (Flight flight : flights) {
            flight.calculatePricePerSeat(classType, this);
        }
    }

    public double getPriceBasedOnSeatType(double basePrice, double extraCharge, double totalSeats, int noOfSeats) {
        if (noOfSeats > totalSeats - (totalSeats * 30) / 100 && noOfSeats < totalSeats) extraCharge = 0;
        else if (noOfSeats > totalSeats - (totalSeats * 50) / 100 && noOfSeats < totalSeats - (totalSeats * 30) / 100)
            extraCharge = (basePrice * 20) / 100;
        else if (noOfSeats > totalSeats - (totalSeats * 75) / 100 && noOfSeats < totalSeats - (totalSeats * 50) / 100)
            extraCharge = (basePrice * 35) / 100;
        else if (noOfSeats < totalSeats - (totalSeats * 75) / 100 && noOfSeats > 0)
            extraCharge = (basePrice * 50) / 100;
        return extraCharge;
    }

    public double getPricePerSeatBasedOnDays(double extraCharge, long days) {
        if (days > 10 && days <= 15) extraCharge = 0;
        else if (days < 10 && days > 3) extraCharge = (double) (days * 2) / 100;
        else if (days < 3 && days > 0) extraCharge = (double) (days * 10) / 100;
        return extraCharge;
    }
}