package com.mickey.airlinereservationsystem.controller;

import com.mickey.airlinereservationsystem.dto.AirportRequest;
import com.mickey.airlinereservationsystem.dto.AirportResponse;
import com.mickey.airlinereservationsystem.service.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<AirportResponse> create(
            @Valid @RequestBody AirportRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        airportService.createAirport(request)
                );
    }

    @GetMapping
    public ResponseEntity<List<AirportResponse>> getAll() {

        return ResponseEntity.ok(
                airportService.getAllAirports()
        );
    }

    @GetMapping("/{code}")
    public ResponseEntity<AirportResponse> getByCode(
            @PathVariable String code
    ) {

        return ResponseEntity.ok(
                airportService.getAirportByCode(code)
        );
    }
}
