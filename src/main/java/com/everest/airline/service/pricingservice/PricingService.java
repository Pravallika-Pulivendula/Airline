package com.everest.airline.service.pricingservice;

public interface PricingService {
    default double getCharge(double basePrice, int totalSeats, int availableSeats) {
        return 0;
    }

    default double getCharge(long days) {
        return 0;
    }
}
