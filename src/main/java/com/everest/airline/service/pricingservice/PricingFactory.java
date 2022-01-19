package com.everest.airline.service.pricingservice;

import com.everest.airline.service.pricingstrategy.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PricingFactory {
    private static final Map<String, PricingStrategy> strategies = new HashMap<>();

    static {
        strategies.put("NO_CHARGE", new NoCharge());
        strategies.put("TWENTY_PERCENT", new TwentyPercent());
        strategies.put("THIRTY_FIVE_PERCENT", new ThirtyFivePercent());
        strategies.put("FIFTY_PERCENT", new FiftyPercent());
        strategies.put("TWO_PERCENT", new TwoPercent());
        strategies.put("TEN_PERCENT", new TenPercent());
    }

    public PricingStrategy getPricingStrategyForSeats(String chargePercent) {
        return strategies.get(chargePercent);
    }

    public PricingStrategy getPricingStrategyForDays(String chargePercent) {
        return strategies.get(chargePercent);
    }
}
