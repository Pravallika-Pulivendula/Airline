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
    private int economicClassSeats;
    private int secondClassSeats;
    private int firstClassSeats;
    private final double economicClassBasePrice;
    private final double firstClassBasePrice;
    private final double secondClassBasePrice;
    private double totalFair = 0;

    public Flight(Long number, String source, String destination, LocalDate departureDate, LocalTime departTime, LocalTime arrivalTime, int availableSeats, int firstClassSeats, int secondClassSeats, int economicClassSeats,double firstClassBasePrice,double secondClassBasePrice,double economicClassBasePrice) {
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
        this.economicClassBasePrice = economicClassBasePrice;
        this.firstClassBasePrice =firstClassBasePrice;
        this.secondClassBasePrice = secondClassBasePrice;
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

    public double updateEconomicClassSeats(int noOfPassengers)
    {
        this.economicClassSeats = this.economicClassSeats - noOfPassengers;
        totalFair = noOfPassengers * this.getEconomicClassBasePrice();
        return totalFair;

    }

    public double updateFirstClassSeats(int noOfPassengers)
    {
        this.firstClassSeats = this.firstClassSeats - noOfPassengers;
        totalFair = noOfPassengers * this.getFirstClassBasePrice();
        return totalFair;
    }

    public double updateSecondClassSeats(int noOfPassengers)
    {
        this.secondClassSeats = this.secondClassSeats - noOfPassengers;
        totalFair = noOfPassengers * this.getSecondClassBasePrice();
        return totalFair;
    }

    public double getEconomicClassBasePrice() {
        return economicClassBasePrice;
    }

    public double getFirstClassBasePrice() {
        return firstClassBasePrice;
    }

    public double getSecondClassBasePrice() {
        return secondClassBasePrice;
    }
}
