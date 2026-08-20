package com.mickey.airlinereservationsystem.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record FlightRequest(

        @NotBlank
        String flightNumber,

        @NotBlank
        @Size(min = 3, max = 3)
        @Pattern(regexp = "^[A-Z]{3}$")
        String departureAirportCode,

        @NotBlank
        @Size(min = 3, max = 3)
        @Pattern(regexp = "^[A-Z]{3}$")
        String arrivalAirportCode,

        @NotNull
        LocalDateTime departureTime,

        @NotNull
        LocalDateTime arrivalTime,

        @NotNull
        @Min(1)
        Integer totalSeats
) {
}