package com.mickey.airlinereservationsystem.dto;

public record AirportResponse(
        Long id,
        String code,
        String name,
        String city,
        String country
) {
}