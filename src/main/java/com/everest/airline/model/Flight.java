package com.everest.airline.model;

import com.everest.airline.enums.ClassType;
import com.everest.airline.service.PricingService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Flight {
    private final Long number;
    private final String source;
    private final String destination;
    private final LocalDate departureDate;
    private final LocalTime departTime;
    private final LocalTime arrivalTime;
    private int totalSeats;
    private double pricePerSeat;
    private final int TOTAL_ECONOMIC_CLASS_SEATS = 50;
    private final int TOTAL_FIRST_CLASS_SEATS = 10;
    private final int TOTAL_SECOND_CLASS_SEATS = 30;


    PricingService pricingService = new PricingService();
    FlightSeatType economicClassSeat;
    FlightSeatType firstClassSeat;
    FlightSeatType secondClassSeat;

    public Flight(Long number, String source, String destination, LocalDate departureDate, LocalTime departTime, LocalTime arrivalTime, int totalSeats, int availableFirstClassSeats, int availableSecondClassSeats, int availableEconomicClassSeats, double firstClassBasePrice, double secondClassBasePrice, double economicClassBasePrice) {
        this.number = number;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departTime = departTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        economicClassSeat = new FlightSeatType(TOTAL_ECONOMIC_CLASS_SEATS, availableEconomicClassSeats, economicClassBasePrice);
        firstClassSeat = new FlightSeatType(TOTAL_FIRST_CLASS_SEATS, availableFirstClassSeats, firstClassBasePrice);
        secondClassSeat = new FlightSeatType(TOTAL_SECOND_CLASS_SEATS, availableSecondClassSeats, secondClassBasePrice);
    }

    @Override
    public String toString() {
        return number + "," + source + "," + destination + "," + departureDate + "," + departTime + "," + arrivalTime + "," + totalSeats + "," + firstClassSeat.getAvailableSeats() + "," + secondClassSeat.getAvailableSeats() + "," + economicClassSeat.getAvailableSeats() + "," + firstClassSeat.getSeatBasePrice() + "," + secondClassSeat.getSeatBasePrice() + "," + economicClassSeat.getSeatBasePrice();
    }

    public FlightSeatType getSeatType(ClassType classType) {
        if (classType == ClassType.Economic) return economicClassSeat;
        if (classType == ClassType.First) return firstClassSeat;
        if (classType == ClassType.Second) return secondClassSeat;
        return null;
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

    public void updateSeats(int noOfPassengers, ClassType classType) {
        this.totalSeats = this.totalSeats - noOfPassengers;
        getSeatType(classType).updateSeats(noOfPassengers);
    }

    public void updatePricePerSeat(String classType) {
        double basePrice = getSeatTypeBasePrice(ClassType.valueOf(classType));
        double extraChargeBasedOnSeats = getChargeBasedOnSeats(classType);
        double extraChargeBasedOnDate = getChargeBasedOnDate();
        double pricePerSeat = basePrice + extraChargeBasedOnSeats + extraChargeBasedOnDate;
        setPricePerSeat(pricePerSeat);
    }

    public double getChargeBasedOnDate() {
        LocalDate date = getDepartureDate();
        LocalDate currentDate = LocalDate.now();
        long days = ChronoUnit.DAYS.between(currentDate, date);
        return pricingService.calculateChargeBasedOnDays(days);
    }

    public double getChargeBasedOnSeats(String classType) {
        double basePrice = getSeatTypeBasePrice(ClassType.valueOf(classType));
        double totalSeats = getTotalClassTypeSeats(ClassType.valueOf(classType));
        int noOfSeats = getAvailableClassTypeSeats(ClassType.valueOf(classType));
        return pricingService.calculateChargeBasedOnSeatType(basePrice,totalSeats, noOfSeats);
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
}
