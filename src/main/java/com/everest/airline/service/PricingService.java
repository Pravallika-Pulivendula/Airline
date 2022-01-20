package com.everest.airline.service;

import java.util.function.Supplier;

public interface PricingService {
    default PricingRule<Double> createPricingRule(Supplier<Boolean> condition, Supplier<Double> process) {
        return new PricingRule<>(condition, process);
    }
}
