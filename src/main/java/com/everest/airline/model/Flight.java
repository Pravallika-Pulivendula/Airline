package com.everest.airline.model;

import com.everest.airline.service.SeatService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Flight {
    private final int economicSeats = 50;
    private final int firstSeats = 10;
    private final int secondSeats = 30;
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
    private double pricePerSeat;

    public Flight(Long number, String source, String destination, LocalDate departureDate, LocalTime departTime, LocalTime arrivalTime, int availableSeats, int firstClassSeats, int secondClassSeats, int economicClassSeats, double firstClassBasePrice, double secondClassBasePrice, double economicClassBasePrice) {
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
        this.firstClassBasePrice = firstClassBasePrice;
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

    public void updateSeats(int noOfPassengers, String classType) {
        this.availableSeats = this.availableSeats - noOfPassengers;
        if (classType.equals("Economic")) this.economicClassSeats = this.economicClassSeats - noOfPassengers;
        if (classType.equals("First")) this.firstClassSeats = this.firstClassSeats - noOfPassengers;
        if (classType.equals("Second")) this.secondClassSeats = this.secondClassSeats - noOfPassengers;
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

    public double getEconomicClassBasePrice() {
        return economicClassBasePrice;
    }

    public double getFirstClassBasePrice() {
        return firstClassBasePrice;
    }

    public double getSecondClassBasePrice() {
        return secondClassBasePrice;
    }

    public double getClassTypeSeatsPrice(String classType) {
        if (classType.equals("Economic")) return getEconomicClassBasePrice();
        if (classType.equals("First")) return getFirstClassBasePrice();
        if (classType.equals("Second")) return getSecondClassBasePrice();
        return 0;
    }

    public double getClassTypeSeats(String classType) {
        if (classType.equals("Economic")) return economicSeats;
        if (classType.equals("First")) return firstSeats;
        if (classType.equals("Second")) return secondSeats;
        return 0;
    }

    public int getNoOfClassTypeSeats(String classType) {
        if (classType.equals("Economic")) return getEconomicClassSeats();
        if (classType.equals("First")) return getFirstClassSeats();
        if (classType.equals("Second")) return getSecondClassSeats();
        return 0;
    }

    public void setPricePerSeat(double pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }

    public double getPricePerSeat() {
        return pricePerSeat;
    }

    public double extraChargeBasedOnDate(SeatService seatService) {
        double extraCharge = 0;
        LocalDate date = getDepartureDate();
        LocalDate currentDate = LocalDate.now();
        long days = ChronoUnit.DAYS.between(currentDate, date);
        return seatService.getPricePerSeatBasedOnDays(extraCharge, days);
    }

    public double extraChargeBasedOnSeats(String classType, SeatService seatService) {
        double basePrice = getClassTypeSeatsPrice(classType);
        double extraCharge = 0;
        double totalSeats = getClassTypeSeats(classType);
        int noOfSeats = getNoOfClassTypeSeats(classType);
        return seatService.getPriceBasedOnSeatType(basePrice, extraCharge, totalSeats, noOfSeats);
    }

    public void calculatePricePerSeat(String classType, SeatService seatService) {
        double basePrice = getClassTypeSeatsPrice(classType);
        double extraChargeBasedOnSeats = extraChargeBasedOnSeats(classType, seatService);
        double extraChargeBasedOnDate = extraChargeBasedOnDate(seatService);
        double pricePerSeat = basePrice + extraChargeBasedOnSeats + extraChargeBasedOnDate;
        setPricePerSeat(pricePerSeat);
    }

}
