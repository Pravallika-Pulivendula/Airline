package com.everest.airline.service;

import org.springframework.stereotype.Component;

@Component
public class PricingService {

    public double calculateChargeBasedOnSeatType(double basePrice, int totalSeats, int noOfSeats) {
        final double THIRTY_PERCENT = 0.3;
        final double FIFTY_PERCENT = 0.5;
        final double SEVENTY_FIVE_PERCENT = 0.75;
        final double TWENTY_PERCENT_EXTRA = 0.2;
        final double THIRTY_FIVE_PERCENT_EXTRA = 0.35;
        final double FIFTY_PERCENT_EXTRA = 0.5;

        if (noOfSeats > totalSeats - (totalSeats * THIRTY_PERCENT) && noOfSeats < totalSeats) return 0;
        if (noOfSeats > totalSeats - (totalSeats * FIFTY_PERCENT) && noOfSeats < totalSeats - (totalSeats * THIRTY_PERCENT))
            return (basePrice * TWENTY_PERCENT_EXTRA);
        if (noOfSeats > totalSeats - (totalSeats * SEVENTY_FIVE_PERCENT) && noOfSeats < totalSeats - (totalSeats * FIFTY_PERCENT))
            return (basePrice * THIRTY_FIVE_PERCENT_EXTRA);
        if (noOfSeats < totalSeats - (totalSeats * SEVENTY_FIVE_PERCENT) && noOfSeats > 0)
            return (basePrice * FIFTY_PERCENT_EXTRA);
        return 0;
    }

    public double calculateChargeBasedOnDays(long days) {
        final int TEN_DAYS_BEFORE = 10;
        final int THREE_DAYS_BEFORE = 3;
        final int FIFTEEN_DAYS_BEFORE = 15;
        final double TWO_PERCENT_EXTRA = 0.02;
        final double TEN_PERCENT_EXTRA = 0.1;

        if (days > TEN_DAYS_BEFORE && days <= FIFTEEN_DAYS_BEFORE) return 0;
        if (days < TEN_DAYS_BEFORE && days > THREE_DAYS_BEFORE) return (days * TWO_PERCENT_EXTRA);
        if (days < THREE_DAYS_BEFORE && days > 0) return (days * TEN_PERCENT_EXTRA);
        return 0;
    }
}