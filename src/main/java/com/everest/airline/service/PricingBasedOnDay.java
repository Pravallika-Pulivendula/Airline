package com.everest.airline.service;

import com.everest.airline.enums.ExtraChargeTypeForSeats;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Component("Days")
public class PricingBasedOnDay implements PricingService {

    public double getCharge(long days) {
        Map<ExtraChargeTypeForSeats, PricingRule<Double>> extraChargePercentRuleMap = setPercentRuleMap(days);
        return Stream.of(ExtraChargeTypeForSeats.values())
                .filter(extraChargePercent -> extraChargePercentRuleMap.get(extraChargePercent).getCondition().get())
                .map(extraChargePercent -> extraChargePercentRuleMap.get(extraChargePercent).getProcess().get())
                .findFirst()
                .orElse((double) 0);
    }

    Map<ExtraChargeTypeForSeats, PricingRule<Double>> setPercentRuleMap(long days) {
        Map<ExtraChargeTypeForSeats, PricingRule<Double>> percentRuleMap = new HashMap<>();
        percentRuleMap.put(ExtraChargeTypeForSeats.NO_CHARGE, zeroPercent(days));
        percentRuleMap.put(ExtraChargeTypeForSeats.TWO_PERCENT, twoPercent(days));
        percentRuleMap.put(ExtraChargeTypeForSeats.TEN_PERCENT, tenPercent(days));
        return percentRuleMap;
    }

    PricingRule<Double> zeroPercent(long days) {
        return createPricingRule(
                () -> (days > 15),
                () -> days * 0.0
        );
    }

    PricingRule<Double> twoPercent(long days) {
        return createPricingRule(
                () -> (days > 10),
                () -> days * 0.02
        );
    }

    PricingRule<Double> tenPercent(long days) {
        return createPricingRule(
                () -> (days <= 3 && days > 0),
                () -> days * 0.1
        );
    }
}
