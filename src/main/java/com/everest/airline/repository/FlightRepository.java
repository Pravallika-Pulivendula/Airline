package com.everest.airline.utils;

import com.everest.airline.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class DatabaseUtil {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public long addNewFlight(Flight flight) {
        KeyHolder flightNumber = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = getSqlParameterSource(flight, new HashMap<>());
        jdbcTemplate.update("insert into flight(source,destination,departureDate,departTime,arrivalTime) values(:source, :destination,:departureDate,:departTime,:arrivalTime)", parameterSource,flightNumber);
        parameterSource = getSqlParameterSource(flight, getFlightMap(flightNumber.getKey().longValue()));
        jdbcTemplate.update("insert into EconomicClass values(:number,:totalEconomicSeats,:availableEconomicSeats,:economicBasePrice)",parameterSource);
        jdbcTemplate.update("insert into FirstClass values(:number,:totalFirstClassSeats,:availableFirstClassSeats,:firstClassBasePrice)", parameterSource);
        jdbcTemplate.update("insert into SecondClass values(:number,:totalSecondClassSeats,:availableSecondClassSeats,:secondClassBasePrice)", parameterSource);
        return Objects.requireNonNull(flightNumber.getKey()).longValue();
    }

    public Flight updateFlight(long number, Flight newFlight) {
        Map<String, Object> flightMap = getFlightMap(number);
        SqlParameterSource parameterSource = getSqlParameterSource(newFlight, flightMap);
        jdbcTemplate.update("update Flight set source =:source, destination =:destination, departureDate =:departureDate, departTime =:departTime, arrivalTime =:arrivalTime where number =:number", parameterSource);
        jdbcTemplate.update("update EconomicClass set totalEconomicSeats =:totalEconomicSeats,availableEconomicSeats =:availableEconomicSeats, economicBasePrice =:economicBasePrice where number =:number" , parameterSource);
        jdbcTemplate.update("update FirstClass set totalFirstClassSeats =:totalFirstClassSeats,availableFirstCLassSeats =:availableFirstClassSeats,firstClassBasePrice =:firstClassBasePrice where number =:number", parameterSource);
        jdbcTemplate.update("update SecondClass set totalSecondClassSeats =:totalSecondClassSeats,availableSecondClassSeats =:availableSecondClassSeats,secondClassBasePrice =:secondClassBasePrice where number =:number", parameterSource);
        return getFlight(number);
    }

    public int deleteFlight(long number) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("number", number);
        return jdbcTemplate.update("delete from flight where number =:number", parameterSource);
    }

    public Flight getFlight(long number) {
        Map<String, Object> flightMap = getFlightMap(number);
        return jdbcTemplate.queryForObject("select f.*,totalEconomicSeats,availableEconomicSeats,economicBasePrice,totalFirstClassSeats,availableFirstClassSeats,firstCLassBasePrice,totalSecondClassSeats,availableSecondClassSeats,secondCLassBasePrice from flight f join EconomicClass e on e.number = f.number and f.number =:number join FirstClass fc on fc.number = f.number join SecondClass s on s.number = f.number", flightMap, new FlightRowMapper());
    }

    private SqlParameterSource getSqlParameterSource(Flight flight, Map<String, Object> numberParam) {
        return new MapSqlParameterSource()
                .addValue("source", flight.getSource())
                .addValue("destination", flight.getDestination())
                .addValue("departureDate", flight.getDepartureDate())
                .addValue("departTime", flight.getDepartTime())
                .addValue("arrivalTime", flight.getArrivalTime())
                .addValue("totalEconomicSeats", flight.getEconomic().getTotalSeats())
                .addValue("availableEconomicSeats", flight.getEconomic().getAvailableSeats())
                .addValue("economicBasePrice", flight.getEconomic().getSeatBasePrice())
                .addValue("totalFirstClassSeats", flight.getFirst().getTotalSeats())
                .addValue("availableFirstClassSeats", flight.getFirst().getAvailableSeats())
                .addValue("firstClassBasePrice", flight.getFirst().getSeatBasePrice())
                .addValue("totalSecondClassSeats", flight.getSecond().getTotalSeats())
                .addValue("availableSecondClassSeats", flight.getSecond().getAvailableSeats())
                .addValue("secondClassBasePrice", flight.getSecond().getSeatBasePrice())
                .addValues(numberParam);
    }

    private Map<String, Object> getFlightMap(long number) {
        Map<String, Object> flightMap = new HashMap<>();
        flightMap.put("number", number);
        return flightMap;
    }
}
