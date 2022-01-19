package com.everest.airline.service.pricingstrategy;

public class ThirtyFivePercent implements PricingStrategy{
    @Override
    public double getExtraCharge(double basePrice) {
        return basePrice * 0.35;
    }
}
