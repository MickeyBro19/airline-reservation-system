package com.mickey.airlinereservationsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AirportRequest(

        @NotBlank
        @Size(min = 3, max = 3)
        @Pattern(regexp = "^[A-Z]{3}$",
                message = "Airport code must contain exactly 3 uppercase letters")
        String code,

        @NotBlank
        String name,

        @NotBlank
        String city,

        @NotBlank
        String country
) {
}