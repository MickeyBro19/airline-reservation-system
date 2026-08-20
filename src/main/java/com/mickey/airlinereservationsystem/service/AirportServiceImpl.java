package com.mickey.airlinereservationsystem.service;

import com.mickey.airlinereservationsystem.dto.AirportRequest;
import com.mickey.airlinereservationsystem.dto.AirportResponse;
import com.mickey.airlinereservationsystem.entity.Airport;
import com.mickey.airlinereservationsystem.exception.BadRequestException;
import com.mickey.airlinereservationsystem.exception.ResourceNotFoundException;
import com.mickey.airlinereservationsystem.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    @Override
    public AirportResponse createAirport(AirportRequest request) {

        String code = request.code().toUpperCase();

        if (!code.matches("^[A-Z]{3}$")) {
            throw new BadRequestException(
                    "Airport code must contain exactly 3 uppercase letters"
            );
        }

        if (airportRepository.existsByCode(code)) {
            throw new BadRequestException(
                    "Airport code already exists"
            );
        }

        Airport airport = Airport.builder()
                .code(code)
                .name(request.name())
                .city(request.city())
                .country(request.country())
                .build();

        Airport saved = airportRepository.save(airport);

        return mapToResponse(saved);
    }

    @Override
    public List<AirportResponse> getAllAirports() {

        return airportRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public AirportResponse getAirportByCode(String code) {

        Airport airport = airportRepository
                .findByCode(code.toUpperCase())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Airport not found"
                        )
                );

        return mapToResponse(airport);
    }

    private AirportResponse mapToResponse(Airport airport) {

        return new AirportResponse(
                airport.getId(),
                airport.getCode(),
                airport.getName(),
                airport.getCity(),
                airport.getCountry()
        );
    }
}