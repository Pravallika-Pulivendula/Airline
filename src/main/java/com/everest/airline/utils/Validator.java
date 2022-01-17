package com.everest.airline.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Component
public class Validator {

    public boolean isStringValid(String string) {
        return string == null || string.trim().isEmpty();
    }

    public boolean areStringsEqual(String from, String to) {
        return from.equals(to);
    }

    public LocalDate parseInputDate(String string) {
        LocalDate date;
        try {
            date = LocalDate.parse(string);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(String.format("Could not parse input date %s, please input a date in yyyy-MM-dd format", string));
        }
        return date;
    }

    public LocalTime parseInputTime(String string) {
        LocalTime time;
        try {
            time = LocalTime.parse(string);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(String.format("Could not parse input time %s, please input a time in hours:minutes format", string));
        }
        return time;
    }

}
