package com.everest.airline.controller;


import com.everest.airline.model.Flight;
import com.everest.airline.utils.DatabaseUtil;
import com.everest.airline.utils.FlightRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class FlightController {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private DatabaseUtil databaseUtil;

    @GetMapping("/flights")
    public List<Flight> getAllFlights() {
        return jdbcTemplate.query("select * from flight", new FlightRowMapper());
    }

    @GetMapping("/flights/{number}")
    public Flight getFlight(@PathVariable("number") long number) {
        return databaseUtil.getFlight(number);
    }

    @PostMapping("/flights")
    public long addFlight(@RequestBody Flight flight) {
        return databaseUtil.addNewFlight(flight);
    }

    @PutMapping("/flights/{number}")
    public Flight updateFlight(@PathVariable("number") long number, @RequestBody Flight newFlight) {
        return databaseUtil.updateFlight(number, newFlight);
    }

    @DeleteMapping("/flights/{number}")
    public int deleteFlight(@PathVariable("number") long number) {
        return databaseUtil.deleteFlight(number);
    }

}
