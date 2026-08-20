package com.mickey.airlinereservationsystem.controller;

import com.mickey.airlinereservationsystem.dto.FlightRequest;
import com.mickey.airlinereservationsystem.dto.FlightResponse;
import com.mickey.airlinereservationsystem.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightResponse> create(
            @Valid @RequestBody FlightRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        flightService.createFlight(request)
                );
    }

    @GetMapping("/{flightNumber}")
    public ResponseEntity<FlightResponse> getByNumber(
            @PathVariable String flightNumber
    ) {

        return ResponseEntity.ok(
                flightService.getFlightByNumber(
                        flightNumber
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<Page<FlightResponse>> search(
            @RequestParam String from,
            @RequestParam String to,

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime start,

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime end,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return ResponseEntity.ok(
                flightService.searchFlights(
                        from,
                        to,
                        start,
                        end,
                        page,
                        size
                )
        );
    }
}