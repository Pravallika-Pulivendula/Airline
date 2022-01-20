package com.everest.airline.service.pricingrule;

import java.util.function.Supplier;

public class PricingRule<T> {
    private final Supplier<Boolean> condition;
    private final Supplier<Double> process;

    public PricingRule(Supplier<Boolean> condition, Supplier<Double> process) {
        this.condition = condition;
        this.process = process;
    }

    public Supplier<Boolean> getCondition() {
        return condition;
    }

    public Supplier<Double> getProcess() {
        return process;
    }
}
