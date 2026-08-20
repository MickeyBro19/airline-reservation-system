package com.mickey.airlinereservationsystem.dto;

import com.mickey.airlinereservationsystem.enums.ReservationStatus;

import java.time.LocalDateTime;

public record ReservationResponse(

        Long id,
        String flightNumber,
        String passengerEmail,
        Integer seatsBooked,
        ReservationStatus status,
        LocalDateTime bookedAt
) {
}