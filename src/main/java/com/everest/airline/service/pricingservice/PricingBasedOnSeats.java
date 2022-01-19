package com.everest.airline.service.pricingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Primary
@Component("Seats")
public class PricingBasedOnSeats implements PricingService{

    @Autowired
    PricingFactory pricingFactory;

    @Override
    public double getCharge(double basePrice, int totalSeats, int availableSeats) {
        String chargePercent = getChargePercentBasedOnSeats(totalSeats, availableSeats);
        return pricingFactory.getPricingStrategyForSeats(chargePercent).getExtraCharge(basePrice);
    }

    public String getChargePercentBasedOnSeats(int totalSeats, int availableSeats) {
        Map<Boolean, String> extraChargePercent = new HashMap<>();
        extraChargePercent.put(noCharge(totalSeats, availableSeats), "NO_CHARGE");
        extraChargePercent.put(twentyPercentCharge(totalSeats, availableSeats), "TWENTY_PERCENT");
        extraChargePercent.put(thirtyFivePercentCharge(totalSeats, availableSeats), "THIRTY_FIVE_PERCENT");
        extraChargePercent.put(fiftyPercentCharge(totalSeats, availableSeats), "FIFTY_PERCENT");
        return extraChargePercent.get(true);
    }

    public boolean noCharge(int totalSeats, int availableSeats) {
        return availableSeats > totalSeats - totalSeats * 0.3 && availableSeats <= totalSeats;
    }

    public boolean twentyPercentCharge(int totalSeats, int availableSeats) {
        return availableSeats > totalSeats - totalSeats * 0.5 && availableSeats <= totalSeats - totalSeats * 0.3;
    }

    public boolean thirtyFivePercentCharge(int totalSeats, int availableSeats) {
        return availableSeats > totalSeats - totalSeats * 0.75 && availableSeats <= totalSeats - totalSeats * 0.5;
    }

    public boolean fiftyPercentCharge(int totalSeats, int availableSeats) {
        return availableSeats > 0 && availableSeats <= totalSeats - totalSeats * 0.75;
    }
}
