package com.everest.airline.service.pricingrule;

import com.everest.airline.enums.ExtraChargeTypeForSeats;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

@Component("Seats")
@Primary
public class PricingBasedOnSeat implements PricingService {

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

    public double getExtraPrice(double basePrice, int totalSeats, int availableSeats) {
        Map<ExtraChargeTypeForSeats, PricingRule<Double>> extraChargePercentRuleMap = setPercentRuleMap(basePrice, totalSeats, availableSeats);
        return Stream.of(ExtraChargeTypeForSeats.values())
                .filter(extraChargeTypeForSeats -> extraChargePercentRuleMap.get(extraChargeTypeForSeats).getCondition().get())
                .map(extraChargeTypeForSeats -> extraChargePercentRuleMap.get(extraChargeTypeForSeats).getProcess().get())
                .findFirst()
                .orElse((double) 0);
    }

    Map<ExtraChargeTypeForSeats, PricingRule<Double>> setPercentRuleMap(double basePrice, int totalSeats, int availableSeats) {
        EnumMap<ExtraChargeTypeForSeats, PricingRule<Double>> percentRuleMap = new EnumMap<>(ExtraChargeTypeForSeats.class);
        percentRuleMap.put(ExtraChargeTypeForSeats.ZERO_PERCENT, zeroPercent(basePrice, totalSeats, availableSeats));
        percentRuleMap.put(ExtraChargeTypeForSeats.TWENTY_PERCENT, twentyPercent(basePrice, totalSeats, availableSeats));
        percentRuleMap.put(ExtraChargeTypeForSeats.THIRTY_FIVE_PERCENT, thirtyFivePercent(basePrice, totalSeats, availableSeats));
        percentRuleMap.put(ExtraChargeTypeForSeats.FIFTY_PERCENT, fiftyPercent(basePrice, totalSeats, availableSeats));
        return percentRuleMap;
    }
}
