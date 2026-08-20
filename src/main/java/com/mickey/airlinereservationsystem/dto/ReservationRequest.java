package com.mickey.airlinereservationsystem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReservationRequest(

        @NotNull
        Long flightId,

        @NotNull
        @Min(1)
        Integer seats
) {
}