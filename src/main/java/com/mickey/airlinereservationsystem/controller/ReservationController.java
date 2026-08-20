package com.mickey.airlinereservationsystem.controller;

import com.mickey.airlinereservationsystem.dto.ReservationRequest;
import com.mickey.airlinereservationsystem.dto.ReservationResponse;
import com.mickey.airlinereservationsystem.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> book(
            @Valid @RequestBody ReservationRequest request,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                reservationService.book(
                        request,
                        authentication.getName()
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<List<ReservationResponse>>
    myReservations(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                reservationService.getMyReservations(
                        authentication.getName()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(
            @PathVariable Long id,
            Authentication authentication
    ) {

        reservationService.cancel(
                id,
                authentication.getName()
        );

        return ResponseEntity.noContent().build();
    }
}
