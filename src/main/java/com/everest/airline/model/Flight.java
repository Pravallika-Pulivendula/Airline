package com.everest.airline.model;

import com.everest.airline.enums.ClassType;
import com.everest.airline.service.PricingBasedOnDay;
import com.everest.airline.service.PricingBasedOnSeat;
import com.everest.airline.service.PricingService;
import com.everest.airline.utils.Validator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.Map;

public class Flight {
    private Long number;
    private String source;
    private String destination;
    private LocalDate departureDate;
    private LocalTime departTime;
    private LocalTime arrivalTime;
    FlightClass economic;
    FlightClass first;
    FlightClass second;
    PricingService pricingService;
    Validator validator;
    Map<ClassType, FlightClass> flightClass;

    public Flight(String source, String destination, LocalDate departureDate, LocalTime departTime, LocalTime arrivalTime, FlightClass economicClass, FlightClass firstClass, FlightClass secondClass, PricingService pricingService, Validator validator) {
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departTime = departTime;
        this.arrivalTime = arrivalTime;
        this.economic = economicClass;
        this.first = firstClass;
        this.second = secondClass;
        this.pricingService = pricingService;
        this.validator = validator;
        setClassType();
    }

    public Flight() {
        setValidator();
    }

    @Override
    public String toString() {
        return number + "," + source + "," + destination + "," + departureDate + "," + departTime + "," + arrivalTime + "," + economic.getAvailableSeats() + "," + economic.getSeatBasePrice() + "," + first.getAvailableSeats() + "," + first.getSeatBasePrice() + "," + second.getAvailableSeats() + "," + second.getSeatBasePrice();
    }

    public FlightClass getClassType(ClassType classType) {
        return flightClass.get(classType);
    }

    public void setClassType() {
        flightClass = new EnumMap<>(ClassType.class);
        flightClass.put(ClassType.Economic, economic);
        flightClass.put(ClassType.First, first);
        flightClass.put(ClassType.Second, second);
    }

    public void reserveSeats(int noOfPassengers, ClassType classType) {
        getClassType(classType).reserveSeats(noOfPassengers);
    }

    public double getPricePerSeat(String classType, PricingBasedOnSeat pricingBasedOnSeat, PricingBasedOnDay pricingBasedOnDay) {
        double basePrice = getSeatTypeBasePrice(ClassType.valueOf(classType));
        int totalAvailableSeats = getAvailableClassTypeSeats(ClassType.valueOf(classType));
        int totalSeats = getTotalClassTypeSeats(ClassType.valueOf(classType));
        long days = Math.abs(ChronoUnit.DAYS.between(getDepartureDate(), LocalDate.now()));
        return basePrice + Math.abs(pricingBasedOnSeat.getCharge(basePrice,totalSeats,totalAvailableSeats)) + Math.abs(pricingBasedOnDay.getCharge(days));
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
        this.departureDate = validator.parseInputDate(departureDate);
    }

    public void setValidator() {
        this.validator = new Validator();
    }

    public void setDepartTime(String departTime) {
        this.departTime = validator.parseInputTime(departTime);
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = validator.parseInputTime(arrivalTime);
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
        return economic.getAvailableSeats() + first.getAvailableSeats() + second.getAvailableSeats();
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public double getSeatTypeBasePrice(ClassType classType) {
        return getClassType(classType).getSeatBasePrice();
    }

    public int getAvailableClassTypeSeats(ClassType classType) {
        return getClassType(classType).getAvailableSeats();
    }

    public int getTotalClassTypeSeats(ClassType classType) {
        return getClassType(classType).getTotalSeats();
    }

    public void setEconomic(FlightClass economic) {
        this.economic = economic;
    }

    public void setFirst(FlightClass first) {
        this.first = first;
    }

    public void setSecond(FlightClass second) {
        this.second = second;
    }
}
