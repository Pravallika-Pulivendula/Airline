package com.everest.airline.model;

import com.everest.airline.enums.ClassType;
import com.everest.airline.service.PricingService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Flight {
    private Long number;
    private String source;
    private String destination;
    private LocalDate departureDate;
    private LocalTime departTime;
    private LocalTime arrivalTime;
    private int totalSeats;
    private double pricePerSeat;
    FlightSeatType economicClassSeatType;
    FlightSeatType firstClassSeatType;
    FlightSeatType secondClassSeatType;

    PricingService pricingService = new PricingService();

    public Flight(Long number, String source, String destination, LocalDate departureDate, LocalTime departTime, LocalTime arrivalTime, int totalSeats, FlightSeatType economicClassSeatType, FlightSeatType firstClassSeatType, FlightSeatType secondClassSeatType) {
        this.number = number;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departTime = departTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.economicClassSeatType = economicClassSeatType;
        this.firstClassSeatType = firstClassSeatType;
        this.secondClassSeatType = secondClassSeatType;
    }

    public Flight() {
    }

    @Override
    public String toString() {
        return number + "," + source + "," + destination + "," + departureDate + "," + departTime + "," + arrivalTime + "," + totalSeats + "," + economicClassSeatType.getAvailableSeats() + "," + economicClassSeatType.getSeatBasePrice() + "," + firstClassSeatType.getAvailableSeats() + "," + firstClassSeatType.getSeatBasePrice() + "," + secondClassSeatType.getAvailableSeats() + "," + secondClassSeatType.getSeatBasePrice();
    }

    public FlightSeatType getSeatType(ClassType classType) {
        if (classType == ClassType.Economic) return economicClassSeatType;
        if (classType == ClassType.First) return firstClassSeatType;
        if (classType == ClassType.Second) return secondClassSeatType;
        return null;
    }

    public void updateSeats(int noOfPassengers, ClassType classType) {
        this.totalSeats = this.totalSeats - noOfPassengers;
        getSeatType(classType).updateSeats(noOfPassengers);
    }

    public void updatePricePerSeat(String classType) {
        double basePrice = getSeatTypeBasePrice(ClassType.valueOf(classType));
        double extraChargeBasedOnSeats = getChargeBasedOnSeats(classType);
        double extraChargeBasedOnDate = getChargeBasedOnDate();
        double seatPrice = basePrice + extraChargeBasedOnSeats + extraChargeBasedOnDate;
        setPricePerSeat(seatPrice);
    }

    public double getChargeBasedOnDate() {
        LocalDate date = getDepartureDate();
        LocalDate currentDate = LocalDate.now();
        long days = ChronoUnit.DAYS.between(currentDate, date);
        return pricingService.calculateChargeBasedOnDays(days);
    }

    public double getChargeBasedOnSeats(String classType) {
        double basePrice = getSeatTypeBasePrice(ClassType.valueOf(classType));
        double totalAvailableSeats = getTotalClassTypeSeats(ClassType.valueOf(classType));
        int noOfSeats = getAvailableClassTypeSeats(ClassType.valueOf(classType));
        return pricingService.calculateChargeBasedOnSeatType(basePrice, totalAvailableSeats, noOfSeats);
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public void setDepartTime(LocalTime departTime) {
        this.departTime = departTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
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

    public int getTotalSeats() {
        return totalSeats;
    }

    public LocalTime getDepartTime() {
        return departTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setPricePerSeat(double pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }

    public double getPricePerSeat() {
        return pricePerSeat;
    }

    public double getSeatTypeBasePrice(ClassType classType) {
        return getSeatType(classType).getSeatBasePrice();
    }

    public int getAvailableClassTypeSeats(ClassType classType) {
        return getSeatType(classType).getAvailableSeats();
    }

    public int getTotalClassTypeSeats(ClassType classType) {
        return getSeatType(classType).getTotalSeats();
    }

    public void setEconomicClassSeatType(FlightSeatType economicClassSeatType) {
        this.economicClassSeatType = economicClassSeatType;
    }

    public void setFirstClassSeatType(FlightSeatType firstClassSeatType) {
        this.firstClassSeatType = firstClassSeatType;
    }

    public void setSecondClassSeatType(FlightSeatType secondClassSeatType) {
        this.secondClassSeatType = secondClassSeatType;
    }

}
