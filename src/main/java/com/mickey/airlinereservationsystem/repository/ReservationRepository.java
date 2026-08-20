package com.mickey.airlinereservationsystem.repository;

import com.mickey.airlinereservationsystem.entity.Reservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository
        extends JpaRepository<Reservation, Long> {

    @EntityGraph(attributePaths = {
            "flight",
            "user"
    })
    List<Reservation> findByUserId(Long userId);
}