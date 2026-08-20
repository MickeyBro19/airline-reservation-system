package com.mickey.airlinereservationsystem.dto;

import java.time.LocalDateTime;

public record FlightResponse(

        Long id,
        String flightNumber,
        String departureAirport,
        String arrivalAirport,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        Integer totalSeats,
        Integer availableSeats
) {
}