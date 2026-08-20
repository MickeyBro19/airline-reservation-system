package com.mickey.airlinereservationsystem.repository;

import com.mickey.airlinereservationsystem.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    boolean existsByCode(String code);

    Optional<Airport> findByCode(String code);
}