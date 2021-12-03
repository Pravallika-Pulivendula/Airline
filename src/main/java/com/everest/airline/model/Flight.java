package com.everest.airline.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Flight {
    private final long number;
    private final String source;
    private final String destination;
    private final LocalDate departureDate;
    private final LocalTime departTime;
    private final LocalTime arrivalTime;
    private int availableSeats;
    private final int totalSeats = 900;

    public Flight(long number, String source, String destination, LocalDate departureDate, LocalTime departTime, LocalTime arrivalTime) {
        this.number = number;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departTime = departTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = totalSeats;
    }

    public long getNumber() {
        return number;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int updateAvailableSeats() {
        return availableSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
}
