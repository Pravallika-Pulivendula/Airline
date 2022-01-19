package com.everest.airline.service.pricingstrategy;

public class TenPercent implements PricingStrategy{
    @Override
    public double getExtraCharge(long days) {
        return days * 0.1;
    }
}
