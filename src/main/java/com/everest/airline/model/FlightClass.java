package com.everest.airline.model;

public class FlightClass {
    private final int totalSeats;
    private int availableSeats;
    private final double seatBasePrice;

    public FlightClass(int totalSeats, int availableSeats, double seatBasePrice) {
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.seatBasePrice = seatBasePrice;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double getSeatBasePrice() {
        return seatBasePrice;
    }

    public void reserveSeats(int noOfPassengers) {
        this.availableSeats = this.availableSeats - noOfPassengers;
    }
}
