package com.codeartify.examples.parking_spot_reservation.service;

import com.codeartify.examples.parking_spot_reservation.dto.ParkingReservationRequest;
import com.codeartify.examples.parking_spot_reservation.dto.ParkingReservationResponse;
import com.codeartify.examples.parking_spot_reservation.model.ParkingReservation;
import com.codeartify.examples.parking_spot_reservation.model.ParkingSpot;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingReservationRepository;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingSpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ResponseEntity<Object> reserveParkingSpot(ParkingReservationRequest request) {
        var reservingMember = request.getReservedBy();
        var startTime = request.getStartTime();
        var endTime = request.getEndTime();

        Long reservationId = null;
        try {
            reservationId = reserveParkingSpot2(startTime, endTime, reservingMember);
        } catch (RuntimeException e) {
            if (e instanceof ReservationShorterThan30MinutesException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Reservation must be at least 30 minutes long.");
            }
            if (e instanceof EndTimeMustBeAfterStartTimeException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("End time must be after start time.");
            }
            if (e instanceof ReservationOutsideOperatingHoursException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Reservations can only be made between 6:00 AM and 10:00 PM.");
            }
            if (e instanceof ActiveReservationExistsException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You already have an active reservation.");
            }
            if (e instanceof NoAvailableSpotsException) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No available spot left.");
            }
        }

        // Build and return the response
        var response = new ParkingReservationResponse();
        response.setReservationId(reservationId);
        response.setReservedBy(reservingMember);
        response.setStartTime(startTime);
        response.setEndTime(endTime);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private Long reserveParkingSpot2(LocalDateTime startTime, LocalDateTime endTime, String reservingMember) {
        // Validate reservation duration
        if (Duration.between(startTime, endTime).toMinutes() < 30) {
            throw new ReservationShorterThan30MinutesException();
        }

        // Ensure the end time is after the start time
        if (endTime.isBefore(startTime)) {
            throw new EndTimeMustBeAfterStartTimeException();
        }

        // Ensure reservation is within operating hours
        if (startTime.toLocalTime().isBefore(OPENING_TIME) || endTime.toLocalTime().isAfter(CLOSING_TIME)) {
            throw new ReservationOutsideOperatingHoursException();
        }

        // Check if the user already has an active reservation
        boolean hasActiveReservation = parkingReservationRepository
                .hasActiveReservation(reservingMember, startTime, endTime);

        if (hasActiveReservation) {
            throw new ActiveReservationExistsException();
        }

        // Find any available spot
        ParkingSpot spot = this.parkingSpotRepository.findAnyAvailableSpot();

        if (spot == null) {
            throw new NoAvailableSpotsException();
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

        return reservation.getId();
    }
}

