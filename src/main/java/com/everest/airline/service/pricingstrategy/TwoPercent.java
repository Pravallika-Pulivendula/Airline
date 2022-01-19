package com.everest.airline.service.pricingstrategy;

public class TwoPercent implements PricingStrategy{
    @Override
    public double getExtraCharge(long days) {
        return days * 0.02;
    }
}
