package com.mickey.airlinereservationsystem.service;

import com.mickey.airlinereservationsystem.dto.ReservationRequest;
import com.mickey.airlinereservationsystem.dto.ReservationResponse;

import java.util.List;

public interface ReservationService {

    ReservationResponse book(
            ReservationRequest request,
            String email
    );

    List<ReservationResponse> getMyReservations(
            String email
    );

    void cancel(
            Long reservationId,
            String email
    );
}