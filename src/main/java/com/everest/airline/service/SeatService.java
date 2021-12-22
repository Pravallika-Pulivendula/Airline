package com.everest.airline.service;

import com.everest.airline.Data;
import com.everest.airline.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Component
public class SeatService {
    @Autowired
    Data data;

    public void updateAvailableSeats(long number, int noOfPassengers, String classType, Flight flights) throws IOException {
        String toReplace = flights.getAvailableSeats() + "," + flights.getFirstClassSeats() + "," + flights.getSecondClassSeats() + "," + flights.getEconomicClassSeats();
        flights.updateSeats(noOfPassengers, classType);
        String toBeReplaced = flights.getAvailableSeats() + "," + flights.getFirstClassSeats() + "," + flights.getSecondClassSeats() + "," + flights.getEconomicClassSeats();
        data.writeDataToFile(number, toReplace, toBeReplaced);
    }

    public void calculatePricePerSeat(Flight flights, String classType) {
        double basePrice = flights.getClassTypeSeatsPrice(classType);
        double extraChargeBasedOnSeats = calculatePriceBasedOnSeats(flights, classType);
        double extraChargeBasedOnDate = calculatePriceBasedOnDate(flights);
        double pricePerSeat = basePrice + extraChargeBasedOnSeats + extraChargeBasedOnDate;
        flights.setPricePerSeat(pricePerSeat);
    }

    public void setPricePerSeatForEachFlight(ArrayList<Flight> flights, String classType) {
        for (Flight flight : flights) {
            calculatePricePerSeat(flight, classType);
        }
    }

    public double calculatePriceBasedOnSeats(Flight flight, String classType) {
        double basePrice = flight.getClassTypeSeatsPrice(classType);
        double extraCharge = 0;
        double totalSeats = flight.getClassTypeSeats(classType);
        int noOfSeats = flight.getNoOfClassTypeSeats(classType);
        if (noOfSeats > totalSeats - (totalSeats * 30) / 100 && noOfSeats < totalSeats) extraCharge = 0;
        else if (noOfSeats > totalSeats - (totalSeats * 50) / 100 && noOfSeats < totalSeats - (totalSeats * 30) / 100)
            extraCharge = (basePrice * 20) / 100;
        else if (noOfSeats > totalSeats - (totalSeats * 75) / 100 && noOfSeats < totalSeats - (totalSeats * 50) / 100)
            extraCharge = (basePrice * 35) / 100;
        else if (noOfSeats < totalSeats - (totalSeats * 75) / 100 && noOfSeats > 0)
            extraCharge = (basePrice * 50) / 100;
        return extraCharge;
    }

    public double calculatePriceBasedOnDate(Flight flight) {
        double extraCharge = 0;
        LocalDate date = flight.getDepartureDate();
        LocalDate currentDate = LocalDate.now();
        long days = ChronoUnit.DAYS.between(currentDate, date);
        if (days > 10 && days <= 15) extraCharge = 0;
        else if (days < 10 && days > 3) extraCharge = (double) (days * 2) / 100;
        else if (days < 3 && days > 0) extraCharge = (double) (days * 10) / 100;
        return extraCharge;
    }

}