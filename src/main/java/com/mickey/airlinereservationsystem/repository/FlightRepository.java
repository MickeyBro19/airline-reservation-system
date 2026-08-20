package com.mickey.airlinereservationsystem.repository;

import com.mickey.airlinereservationsystem.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    boolean existsByFlightNumber(String flightNumber);

    Optional<Flight> findByFlightNumber(String flightNumber);

    @Query(
            value = """
                    SELECT f
                    FROM Flight f
                    JOIN FETCH f.departureAirport
                    JOIN FETCH f.arrivalAirport
                    WHERE f.departureAirport.code = :from
                      AND f.arrivalAirport.code = :to
                      AND f.departureTime >= :start
                      AND f.departureTime <= :end
                    """,
            countQuery = """
                    SELECT COUNT(f)
                    FROM Flight f
                    WHERE f.departureAirport.code = :from
                      AND f.arrivalAirport.code = :to
                      AND f.departureTime >= :start
                      AND f.departureTime <= :end
                    """
    )
    Page<Flight> searchFlights(
            @Param("from") String from,
            @Param("to") String to,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );
}