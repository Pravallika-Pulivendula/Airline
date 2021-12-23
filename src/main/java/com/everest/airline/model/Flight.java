package com.everest.airline.model;

import com.everest.airline.DataHandler;
import com.everest.airline.enums.ClassType;
import com.everest.airline.service.PricingService;

import java.io.IOException;
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
    private double pricePerSeat;

    DataHandler data = new DataHandler();

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

    public void setPricePerSeat(double pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }

    public double getPricePerSeat() {
        return this.pricePerSeat;
    }

    public double getClassTypeSeatsPrice(ClassType classType) {
        if (classType == ClassType.Economic) return getEconomicClassBasePrice();
        if (classType == ClassType.First) return getFirstClassBasePrice();
        if (classType == ClassType.Second) return getSecondClassBasePrice();
        return 0;
    }

    public double getClassTypeSeats(ClassType classType) {
        int economicSeats = 50;
        if (classType == ClassType.Economic) return economicSeats;
        int firstSeats = 10;
        if (classType == ClassType.First) return firstSeats;
        int secondSeats = 30;
        if (classType == ClassType.Second) return secondSeats;
        return 0;
    }

    public int getNoOfClassTypeSeats(ClassType classType) {
        if (classType == ClassType.Economic) return getEconomicClassSeats();
        if (classType == ClassType.First) return getFirstClassSeats();
        if (classType == ClassType.Second) return getSecondClassSeats();
        return 0;
    }

    public void updateSeats(int noOfPassengers, ClassType classType) {
        this.availableSeats = this.availableSeats - noOfPassengers;
        if (classType == ClassType.Economic) {
            this.economicClassSeats = this.economicClassSeats - noOfPassengers;
        } else if (classType == ClassType.First) {
            this.firstClassSeats = this.firstClassSeats - noOfPassengers;
        } else {
            this.secondClassSeats = this.secondClassSeats - noOfPassengers;
        }
    }

    public void updateAllSeats(long number, int noOfPassengers, String classType) throws IOException {
        String toReplace = getAvailableSeats() + "," + getFirstClassSeats() + "," + getSecondClassSeats() + "," + getEconomicClassSeats();
        updateSeats(noOfPassengers, ClassType.valueOf(classType));
        String toBeReplaced = getAvailableSeats() + "," + getFirstClassSeats() + "," + getSecondClassSeats() + "," + getEconomicClassSeats();
        data.writeDataToFile(number, toReplace, toBeReplaced);
    }
}
