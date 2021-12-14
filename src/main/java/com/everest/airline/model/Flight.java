package com.everest.airline.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Flight {
    private final Long number;
    private final String source;
    private final String destination;
    private final LocalDate departureDate;
    private final LocalTime departTime;
    private final LocalTime arrivalTime;
    private int availableSeats;
    private final int economicClassSeats;
    private final int secondClassSeats;
    private final int firstClassSeats;

    public Flight(Long number, String source, String destination, LocalDate departureDate, LocalTime departTime, LocalTime arrivalTime, int availableSeats, int economicClassSeats, int firstClassSeats, int secondClassSeats ) {
        this.number = number;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departTime = departTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
        this.economicClassSeats = economicClassSeats;
        this.secondClassSeats = secondClassSeats;
        this.firstClassSeats = firstClassSeats;
    }

    public Long getNumber() {
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

    public int getAvailableSeats() {
        return availableSeats;
    }

    public LocalTime getDepartTime() {
        return departTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void updateAvailableSeats(int noOfPassengers) {
        this.availableSeats = this.availableSeats - noOfPassengers;
    }

    public int getEconomicClassSeats() {
        return economicClassSeats;
    }

    public int getSecondClassSeats() {
        return secondClassSeats;
    }

    public int getFirstClassSeats() {
        return firstClassSeats;
    }
}
