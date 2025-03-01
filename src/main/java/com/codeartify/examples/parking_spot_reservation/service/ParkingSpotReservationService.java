package com.codeartify.examples.parking_spot_reservation.service;

import com.codeartify.examples.parking_spot_reservation.model.ParkingReservation;
import com.codeartify.examples.parking_spot_reservation.model.ParkingSpot;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingReservationRepository;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingSpotRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class ParkingSpotReservationService {
    private final ParkingReservationRepository parkingReservationRepository;
    private final ParkingSpotRepository parkingSpotRepository;

    private static final LocalTime OPENING_TIME = LocalTime.of(6, 0); // 6:00 AM
    private static final LocalTime CLOSING_TIME = LocalTime.of(22, 0); // 10:00 PM

    @Transactional
    public ParkingReservationResult reserveParkingSpot(LocalDateTime startTime, LocalDateTime endTime, String reservingMember) {
        // Validate reservation duration
        if (Duration.between(startTime, endTime).toMinutes() < 30) {
            throw new ReservationTimeShorterThanMinimalTime();
        }
        // Ensure the end time is after the start time
        if (endTime.isBefore(startTime)) {
            throw new EndTimeBeforeStartTimeException();
        }
        // Ensure reservation is within operating hours
        if (startTime.toLocalTime().isBefore(OPENING_TIME) || endTime.toLocalTime().isAfter(CLOSING_TIME)) {
            throw new ReservationOutsideOperatingTimeException();
        }
        // Check if the user already has an active reservation
        boolean hasActiveReservation = parkingReservationRepository
                .hasActiveReservation(reservingMember, startTime, endTime);

        if (hasActiveReservation) {
            throw new ActiveReservationException();
        }

        // Find any available spot
        ParkingSpot spot = this.parkingSpotRepository.findAnyAvailableSpot();

        if (spot == null) {
            throw new NoAvailableSpotLeftException();
        }

        // Create and save the reservation
        ParkingReservation reservation = new ParkingReservation(
                reservingMember,
                spot.getId(),
                startTime,
                endTime);
        this.parkingReservationRepository.save(reservation);

        // Mark the parking spot as unavailable
        spot.setAvailable(false);
        this.parkingSpotRepository.save(spot);

        // Build and return the response
        return new ParkingReservationResult(reservation.getId(), reservingMember, startTime, endTime);
    }
}

