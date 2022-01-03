package com.everest.airline.model;

import com.everest.airline.enums.ClassType;
import com.everest.airline.service.PricingService;
import com.everest.airline.utils.ValidateInput;

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
    private double pricePerSeat;
    FlightSeatType economicClass;
    FlightSeatType firstClass;
    FlightSeatType secondClass;
    PricingService pricingService;
    ValidateInput validateInput;

    public Flight(String source, String destination, LocalDate departureDate, LocalTime departTime, LocalTime arrivalTime, FlightSeatType economicClass, FlightSeatType firstClass, FlightSeatType secondClass) {
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departTime = departTime;
        this.arrivalTime = arrivalTime;
        this.economicClass = economicClass;
        this.firstClass = firstClass;
        this.secondClass = secondClass;
        pricingService = new PricingService();
        validateInput = new ValidateInput();
    }

    public Flight() {
    }

    @Override
    public String toString() {
        return number + "," + source + "," + destination + "," + departureDate + "," + departTime + "," + arrivalTime + "," + economicClass.getAvailableSeats() + "," + economicClass.getSeatBasePrice() + "," + firstClass.getAvailableSeats() + "," + firstClass.getSeatBasePrice() + "," + secondClass.getAvailableSeats() + "," + secondClass.getSeatBasePrice();
    }

    public FlightSeatType getSeatType(ClassType classType) {
        if (classType == ClassType.Economic) return economicClass;
        if (classType == ClassType.First) return firstClass;
        if (classType == ClassType.Second) return secondClass;
        return null;
    }

    public void updateSeats(int noOfPassengers, ClassType classType) {
        getSeatType(classType).updateSeats(noOfPassengers);
    }

    public void updatePricePerSeat(String classType) {
        double basePrice = getSeatTypeBasePrice(ClassType.valueOf(classType));
        int totalAvailableSeats = getAvailableClassTypeSeats(ClassType.valueOf(classType));
        int totalSeats = getTotalClassTypeSeats(ClassType.valueOf(classType));
        long days = ChronoUnit.DAYS.between(getDepartureDate(), LocalDate.now());
        double seatPrice = basePrice + pricingService.calculateChargeBasedOnSeatType(basePrice, totalSeats, totalAvailableSeats) + pricingService.calculateChargeBasedOnDays(days);
        setPricePerSeat(seatPrice);
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

    public void setDepartureDate(String departureDate) {
        this.departureDate = validateInput.parseInputDate(departureDate);
    }

    public void setDepartTime(String departTime) {
        this.departTime = validateInput.parseInputTime(departTime);
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = validateInput.parseInputTime(arrivalTime);
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

    public LocalTime getDepartTime() {
        return departTime;
    }

    public int getTotalAvailableSeats() {
        return economicClass.getAvailableSeats() + firstClass.getAvailableSeats() + secondClass.getAvailableSeats();
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

    public void setEconomicClass(FlightSeatType economicClass) {
        this.economicClass = economicClass;
    }

    public void setFirstClass(FlightSeatType firstClass) {
        this.firstClass = firstClass;
    }

    public void setSecondClass(FlightSeatType secondClass) {
        this.secondClass = secondClass;
    }
}
