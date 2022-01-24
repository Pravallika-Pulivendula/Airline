package com.everest.airline.service.pricingrule;

import com.everest.airline.enums.ExtraChargeTypeForDays;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

@Component("Days")
public class PricingBasedOnDay implements PricingService {

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

    public double getExtraPrice(long days) {
        Map<ExtraChargeTypeForDays, PricingRule<Double>> extraChargePercentRuleMap = setPercentRuleMap(days);
        return Stream.of(ExtraChargeTypeForDays.values())
                .filter(extraChargePercent -> extraChargePercentRuleMap.get(extraChargePercent).getCondition().get())
                .map(extraChargePercent -> extraChargePercentRuleMap.get(extraChargePercent).getProcess().get())
                .findFirst()
                .orElse((double) 0);
    }

    Map<ExtraChargeTypeForDays, PricingRule<Double>> setPercentRuleMap(long days) {
        EnumMap<ExtraChargeTypeForDays, PricingRule<Double>> percentRuleMap = new EnumMap<>(ExtraChargeTypeForDays.class);
        percentRuleMap.put(ExtraChargeTypeForDays.NO_CHARGE, zeroPercent(days));
        percentRuleMap.put(ExtraChargeTypeForDays.TWO_PERCENT, twoPercent(days));
        percentRuleMap.put(ExtraChargeTypeForDays.TEN_PERCENT, tenPercent(days));
        return percentRuleMap;
    }
}
