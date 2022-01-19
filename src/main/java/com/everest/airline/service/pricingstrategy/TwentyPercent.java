package com.everest.airline.service.pricingstrategy;

public class TwentyPercent implements PricingStrategy{
    @Override
    public double getExtraCharge(double basePrice) {
        return basePrice * 0.2;
    }
}
