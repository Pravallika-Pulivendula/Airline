package com.everest.airline.service;

import com.everest.airline.model.Flight;

import java.util.List;
import java.util.stream.Collectors;

public class SearchService {
    public List<Flight> searchDepartureDate(String from, String to, String departureDate, List<Flight> flightsData)
    {
        return flightsData.stream()
                .filter(flight -> flight.getSource().equals(from) && flight.getDestination().equals(to) && flight.getDepartureDate().toString().equals(departureDate) && flight.getAvailableSeats() != 0)
                .collect(Collectors.toList());
    }
}
