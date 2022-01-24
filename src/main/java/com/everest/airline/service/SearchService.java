package com.everest.airline.service;

import com.everest.airline.enums.ClassType;
import com.everest.airline.model.Flight;
import com.everest.airline.utils.FlightRowMapper;
import com.everest.airline.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SearchService {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private Validator validator;

    public List<Flight> searchFlights(String from, String to, String departureDate, String classType, int noOfPassengers) {
        if (validator.isStringValid(from) || validator.isStringValid(to) || validator.isStringValid(departureDate) || validator.areStringsEqual(from, to))
            throw new IllegalArgumentException("Arguments are invalid");
        List<Flight> flights;
        Map<String, Object> flightMap = new HashMap<>();
        flightMap.put("from", from);
        flightMap.put("to", to);
        flightMap.put("departureDate", departureDate);
        flights = jdbcTemplate.query("select * from flight where source =:from and destination =:to and departureDate =:departureDate", flightMap, new FlightRowMapper());
        return flights.stream().filter(flight -> flight.getClassType(ClassType.valueOf(classType)).getAvailableSeats() >= noOfPassengers).collect(Collectors.toList());
    }
}

