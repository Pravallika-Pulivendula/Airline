package com.everest.airline.service.pricingstrategy;

public class NoCharge implements PricingStrategy {
    @Override
    public double getExtraCharge(double basePrice) {
        return basePrice * 0;
    }
}
