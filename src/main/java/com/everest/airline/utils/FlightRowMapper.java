package com.everest.airline.utils;

import com.everest.airline.model.Flight;
import com.everest.airline.model.FlightClass;
import com.everest.airline.service.pricingrule.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FlightRowMapper implements RowMapper<Flight> {
    @Autowired
    PricingService pricingService;
    @Autowired
    Validator validator;
    @Override
    public Flight mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Flight(rs.getLong("number"),rs.getString("source"), rs.getString("destination"), rs.getDate("departureDate").toLocalDate(), rs.getTime("departTime").toLocalTime(), rs.getTime("arrivalTime").toLocalTime(), new FlightClass(rs.getInt("totalEconomicSeats"), rs.getInt("availableEconomicSeats"), rs.getDouble("economicBasePrice")), new FlightClass(rs.getInt("totalFirstClassSeats"), rs.getInt("availableFirstClassSeats"), rs.getDouble("firstClassBasePrice")), new FlightClass(rs.getInt("totalSecondClassSeats"), rs.getInt("availableSecondClassSeats"), rs.getDouble("secondClassBasePrice")), pricingService, validator);
    }
}
