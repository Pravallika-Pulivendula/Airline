package com.everest.airline.service;

import com.everest.airline.Data;
import com.everest.airline.model.Flight;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SeatService {

    public void updateAvailableSeats(long number, int noOfPassengers,List<Flight> flightsData) throws IOException {
        Flight flights = flightsData.stream().filter(flight -> flight.getNumber() == number)
                .collect(Collectors.toList()).get(0);
        String toReplace = flights.getNumber() + "," + flights.getSource() + "," + flights.getDestination() + "," + flights.getDepartureDate() + "," + flights.getDepartTime() + "," + flights.getArrivalTime() + "," + flights.getAvailableSeats();
        flights.updateAvailableSeats(noOfPassengers);
        String toBeReplaced = flights.getNumber() + "," + flights.getSource() + "," + flights.getDestination() + "," + flights.getDepartureDate() + "," + flights.getDepartTime() + "," + flights.getArrivalTime() + "," + flights.getAvailableSeats();
        Data.writeDataToFile(number,toReplace,toBeReplaced);
    }

}
