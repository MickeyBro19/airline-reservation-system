package com.mickey.airlinereservationsystem.service;

import com.mickey.airlinereservationsystem.dto.FlightRequest;
import com.mickey.airlinereservationsystem.dto.FlightResponse;
import com.mickey.airlinereservationsystem.entity.Airport;
import com.mickey.airlinereservationsystem.entity.Flight;
import com.mickey.airlinereservationsystem.exception.BadRequestException;
import com.mickey.airlinereservationsystem.exception.ResourceNotFoundException;
import com.mickey.airlinereservationsystem.repository.AirportRepository;
import com.mickey.airlinereservationsystem.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    @Override
    public FlightResponse createFlight(FlightRequest request) {

        String flightNumber =
                request.flightNumber().trim().toUpperCase();

        if (flightRepository.existsByFlightNumber(flightNumber)) {

            throw new BadRequestException(
                    "Flight number already exists"
            );
        }

        Airport departure = getAirport(
                request.departureAirportCode()
        );

        Airport arrival = getAirport(
                request.arrivalAirportCode()
        );

        if (departure.getId().equals(arrival.getId())) {

            throw new BadRequestException(
                    "Departure and arrival airports cannot be the same"
            );
        }

        if (!request.departureTime()
                .isBefore(request.arrivalTime())) {

            throw new BadRequestException(
                    "Departure time must be before arrival time"
            );
        }

        Flight flight = Flight.builder()
                .flightNumber(flightNumber)
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .departureTime(request.departureTime())
                .arrivalTime(request.arrivalTime())
                .totalSeats(request.totalSeats())
                .availableSeats(request.totalSeats())
                .build();

        Flight saved = flightRepository.save(flight);

        return mapToResponse(saved);
    }

    @Override
    public FlightResponse getFlightByNumber(String flightNumber) {

        Flight flight = flightRepository
                .findByFlightNumber(
                        flightNumber.toUpperCase()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Flight not found"
                        )
                );

        return mapToResponse(flight);
    }

    @Override
    public Page<FlightResponse> searchFlights(
            String from,
            String to,
            LocalDateTime start,
            LocalDateTime end,
            int page,
            int size
    ) {

        Pageable pageable =
                PageRequest.of(page, size);

        Page<Flight> flights =
                flightRepository.searchFlights(
                        from.toUpperCase(),
                        to.toUpperCase(),
                        start,
                        end,
                        pageable
                );

        return flights.map(this::mapToResponse);
    }

    private Airport getAirport(String code) {

        return airportRepository
                .findByCode(code.toUpperCase())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Airport not found: " + code
                        )
                );
    }

    private FlightResponse mapToResponse(Flight flight) {

        return new FlightResponse(
                flight.getId(),
                flight.getFlightNumber(),
                flight.getDepartureAirport().getCode(),
                flight.getArrivalAirport().getCode(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getTotalSeats(),
                flight.getAvailableSeats()
        );
    }
}