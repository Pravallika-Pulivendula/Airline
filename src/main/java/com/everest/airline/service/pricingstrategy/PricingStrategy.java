package com.everest.airline.service.pricingstrategy;

public interface PricingStrategy {
    default double getExtraCharge(double basePrice){
        return 0;
    }
    default double getExtraCharge(long days){
        return 0;
    }
}
