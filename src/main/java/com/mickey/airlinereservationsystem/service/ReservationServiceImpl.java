package com.mickey.airlinereservationsystem.service;

import com.mickey.airlinereservationsystem.dto.ReservationRequest;
import com.mickey.airlinereservationsystem.dto.ReservationResponse;
import com.mickey.airlinereservationsystem.entity.Flight;
import com.mickey.airlinereservationsystem.entity.Reservation;
import com.mickey.airlinereservationsystem.entity.User;
import com.mickey.airlinereservationsystem.enums.ReservationStatus;
import com.mickey.airlinereservationsystem.exception.BadRequestException;
import com.mickey.airlinereservationsystem.exception.ResourceNotFoundException;
import com.mickey.airlinereservationsystem.repository.FlightRepository;
import com.mickey.airlinereservationsystem.repository.ReservationRepository;
import com.mickey.airlinereservationsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ReservationResponse book(
            ReservationRequest request,
            String email
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        Flight flight = flightRepository
                .findById(request.flightId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Flight not found"
                        )
                );

        if (request.seats() > flight.getAvailableSeats()) {

            throw new BadRequestException(
                    "Not enough seats available"
            );
        }

        flight.setAvailableSeats(
                flight.getAvailableSeats() - request.seats()
        );

        Reservation reservation = Reservation.builder()
                .user(user)
                .flight(flight)
                .seatsBooked(request.seats())
                .status(ReservationStatus.CONFIRMED)
                .bookedAt(LocalDateTime.now())
                .build();

        Reservation saved =
                reservationRepository.save(reservation);

        return mapToResponse(saved);
    }

    @Override
    public List<ReservationResponse> getMyReservations(
            String email
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        return reservationRepository
                .findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void cancel(
            Long reservationId,
            String email
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        Reservation reservation =
                reservationRepository.findById(reservationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Reservation not found"
                                )
                        );

        if (!reservation.getUser()
                .getId()
                .equals(user.getId())) {

            throw new BadRequestException(
                    "You cannot cancel this reservation"
            );
        }

        if (reservation.getStatus()
                == ReservationStatus.CANCELLED) {

            throw new BadRequestException(
                    "Reservation already cancelled"
            );
        }

        Flight flight = reservation.getFlight();

        flight.setAvailableSeats(
                flight.getAvailableSeats()
                        + reservation.getSeatsBooked()
        );

        reservation.setStatus(
                ReservationStatus.CANCELLED
        );

        reservationRepository.save(reservation);
    }

    private ReservationResponse mapToResponse(
            Reservation reservation
    ) {

        return new ReservationResponse(
                reservation.getId(),
                reservation.getFlight().getFlightNumber(),
                reservation.getUser().getEmail(),
                reservation.getSeatsBooked(),
                reservation.getStatus(),
                reservation.getBookedAt()
        );
    }
}