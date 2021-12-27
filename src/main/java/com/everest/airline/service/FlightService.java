package com.everest.airline.service;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

@Component
public class FlightService {
    File directory = new File("src/main/java/com/everest/airline/flights");
    File[] files = directory.listFiles();

    public long getNextFlightNumber() {
        long number;
        Arrays.sort(files);
        number = Long.parseLong(files[files.length-1].getName())+1;
        return number;
    }

    public String toString(long number,String source,String destination,String departureDate,String departTime,String arrivalTime,int availableSeats,int firstClassSeats,int secondClassSeats,int economicClassSeats,double firstClassBasePrice,double secondClassBasePrice,double economicClassBasePrice) {
        return number+","+source+","+destination+","+departureDate+","+departTime+","+arrivalTime+","+availableSeats+","+firstClassSeats+","+secondClassSeats+","+economicClassSeats+","+firstClassBasePrice+","+secondClassBasePrice+","+economicClassBasePrice;
    }
}
