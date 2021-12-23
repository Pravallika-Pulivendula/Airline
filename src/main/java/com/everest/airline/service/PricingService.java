package com.everest.airline.service;

import com.everest.airline.enums.ClassType;
import com.everest.airline.model.Flight;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Component
public class PricingService {

    public double calculateChargeBasedOnSeatType(double basePrice, double extraCharge, double totalSeats, int noOfSeats) {
        if (noOfSeats > totalSeats - (totalSeats * 30) / 100 && noOfSeats < totalSeats) extraCharge = 0;
        else if (noOfSeats > totalSeats - (totalSeats * 50) / 100 && noOfSeats < totalSeats - (totalSeats * 30) / 100)
            extraCharge = (basePrice * 20) / 100;
        else if (noOfSeats > totalSeats - (totalSeats * 75) / 100 && noOfSeats < totalSeats - (totalSeats * 50) / 100)
            extraCharge = (basePrice * 35) / 100;
        else if (noOfSeats < totalSeats - (totalSeats * 75) / 100 && noOfSeats > 0)
            extraCharge = (basePrice * 50) / 100;
        return extraCharge;
    }

    public double calculateChargePerSeatBasedOnDays(double extraCharge, long days) {
        if (days > 10 && days <= 15) extraCharge = 0;
        else if (days < 10 && days > 3) extraCharge = (double) (days * 2) / 100;
        else if (days < 3 && days > 0) extraCharge = (double) (days * 10) / 100;
        return extraCharge;
    }

    public double getChargeBasedOnDate(Flight flight) {
        double extraCharge = 0;
        LocalDate date = flight.getDepartureDate();
        LocalDate currentDate = LocalDate.now();
        long days = ChronoUnit.DAYS.between(currentDate, date);
        return calculateChargePerSeatBasedOnDays(extraCharge, days);
    }

    public double getChargeBasedOnSeats(String classType, Flight flight) {
        double basePrice = flight.getClassTypeSeatsPrice(ClassType.valueOf(classType));
        double extraCharge = 0;
        double totalSeats = flight.getClassTypeSeats(ClassType.valueOf(classType));
        int noOfSeats = flight.getNoOfClassTypeSeats(ClassType.valueOf(classType));
        return calculateChargeBasedOnSeatType(basePrice, extraCharge, totalSeats, noOfSeats);
    }

    public void updatePricePerSeat(String classType, Flight flight) {
        double pricePerSeat;
        double basePrice = flight.getClassTypeSeatsPrice(ClassType.valueOf(classType));
        double extraChargeBasedOnSeats = getChargeBasedOnSeats(classType, flight);
        double extraChargeBasedOnDate = getChargeBasedOnDate(flight);
        pricePerSeat = basePrice + extraChargeBasedOnSeats + extraChargeBasedOnDate;
        flight.setPricePerSeat(pricePerSeat);
    }

    public void setPricePerSeatForEachFlight(ArrayList<Flight> flights, String classType) {
        for (Flight flight : flights) {
            updatePricePerSeat(classType, flight);
        }
    }
}