package com.mickey.airlinereservationsystem.service;

import com.mickey.airlinereservationsystem.dto.AirportRequest;
import com.mickey.airlinereservationsystem.dto.AirportResponse;

import java.util.List;

public interface AirportService {

    AirportResponse createAirport(AirportRequest request);

    List<AirportResponse> getAllAirports();

    AirportResponse getAirportByCode(String code);
}