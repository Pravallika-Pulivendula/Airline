package com.everest.airline.service.pricingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("Days")
public class PricingBasedOnDays implements PricingService {
    @Autowired
    PricingFactory pricingFactory;

    public String getChargePercentBasedOnDays(long days) {
        Map<Boolean, String> extraChargePercent = new HashMap<>();
        extraChargePercent.put(noCharge(days), "NO_CHARGE");
        extraChargePercent.put(twoPercent(days), "TWO_PERCENT");
        extraChargePercent.put(tenPercent(days), "TEN_PERCENT");
        return extraChargePercent.get(true);
    }

    @Override
    public double getCharge(long days) {
        String chargePercent = getChargePercentBasedOnDays(days);
        return pricingFactory.getPricingStrategyForDays(chargePercent).getExtraCharge(days);
    }

    public boolean twoPercent(long days) {
        return days <= 10 && days > 3;
    }

    public boolean tenPercent(long days) {
        return days > 0 && days <= 3;
    }

    public boolean noCharge(long days) {
        return days > 10 && days <= 15;
    }
}
