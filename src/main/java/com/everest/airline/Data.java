package com.everest.airline;

import com.everest.airline.model.Flight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Data {
    static List<Flight> flights = List.of(
            new Flight(1001, "Hyderabad", "Bangalore",LocalDate.of(2021,12,2), LocalTime.of(2,30),LocalTime.of(2,30)),
            new Flight(1002, "Bangalore", "Hyderabad",LocalDate.of(2021,12,4),LocalTime.of(2,40),LocalTime.of(3,20)),
            new Flight(1003, "Goa", "Bangalore",LocalDate.of(2021,12,5),LocalTime.of(6,20),LocalTime.of(0,30)),
            new Flight(1004, "Bangalore", "Goa",LocalDate.of(2021,12,3),LocalTime.of(0,1),LocalTime.of(2,30)),
            new Flight(1005, "Bangalore", "Hyderabad",LocalDate.of(2021,12,2),LocalTime.of(2,45),LocalTime.of(3,20)),
            new Flight(1006, "Hyderabad", "Bangalore",LocalDate.of(2021,12,3),LocalTime.of(1,20),LocalTime.of(3,29)));

    public static List<Flight> getFlights() {
        return flights;
    }
}
