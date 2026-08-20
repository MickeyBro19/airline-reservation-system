package com.mickey.airlinereservationsystem.service;

import com.mickey.airlinereservationsystem.dto.FlightRequest;
import com.mickey.airlinereservationsystem.dto.FlightResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface FlightService {

    FlightResponse createFlight(FlightRequest request);

    FlightResponse getFlightByNumber(String flightNumber);

    Page<FlightResponse> searchFlights(
            String from,
            String to,
            LocalDateTime start,
            LocalDateTime end,
            int page,
            int size
    );
}