package com.everest.airline.service;

import com.everest.airline.enums.ExtraChargeTypeForDays;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Component("Seats")
@Primary
public class PricingBasedOnSeat implements PricingService {

    public double getCharge(double basePrice, int totalSeats, int availableSeats) {
        Map<ExtraChargeTypeForDays, PricingRule<Double>> extraChargePercentRuleMap = setPercentRuleMap(basePrice, totalSeats, availableSeats);
        return Stream.of(ExtraChargeTypeForDays.values())
                .filter(extraChargeTypeForDays -> extraChargePercentRuleMap.get(extraChargeTypeForDays).getCondition().get())
                .map(extraChargeTypeForDays -> extraChargePercentRuleMap.get(extraChargeTypeForDays).getProcess().get())
                .findFirst()
                .orElse((double) 0);
    }

    Map<ExtraChargeTypeForDays, PricingRule<Double>> setPercentRuleMap(double basePrice, int totalSeats, int availableSeats) {
        Map<ExtraChargeTypeForDays, PricingRule<Double>> percentRuleMap = new HashMap<>();
        percentRuleMap.put(ExtraChargeTypeForDays.ZERO_PERCENT, zeroPercent(basePrice, totalSeats, availableSeats));
        percentRuleMap.put(ExtraChargeTypeForDays.TWENTY_PERCENT, twentyPercent(basePrice, totalSeats, availableSeats));
        percentRuleMap.put(ExtraChargeTypeForDays.THIRTY_FIVE_PERCENT, thirtyFivePercent(basePrice, totalSeats, availableSeats));
        percentRuleMap.put(ExtraChargeTypeForDays.FIFTY_PERCENT, fiftyPercent(basePrice, totalSeats, availableSeats));
        return percentRuleMap;
    }

    PricingRule<Double> zeroPercent(double basePrice, int totalSeats, int availableSeats) {
        return createPricingRule(
                () -> (availableSeats > (totalSeats - totalSeats * 0.3) && availableSeats <= totalSeats),
                () -> basePrice * 0.0
        );
    }

    PricingRule<Double> twentyPercent(double basePrice, int totalSeats, int availableSeats) {
        return createPricingRule(
                () -> (availableSeats > (totalSeats - totalSeats * 0.5) && availableSeats <= (totalSeats - totalSeats * 0.3)),
                () -> basePrice * 0.2
        );
    }

    PricingRule<Double> thirtyFivePercent(double basePrice, int totalSeats, int availableSeats) {
        return createPricingRule(
                () -> (availableSeats > (totalSeats - totalSeats * 0.75) && availableSeats <= (totalSeats - totalSeats * 0.5)),
                () -> basePrice * 0.35
        );
    }

    PricingRule<Double> fiftyPercent(double basePrice, int totalSeats, int availableSeats) {
        return createPricingRule(
                () -> (availableSeats <= (totalSeats - totalSeats * 0.75) && availableSeats > 0),
                () -> basePrice * 0.50
        );
    }
}
