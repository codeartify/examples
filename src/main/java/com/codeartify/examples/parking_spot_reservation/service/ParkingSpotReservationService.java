package com.codeartify.examples.parking_spot_reservation.service;

import lombok.AllArgsConstructor;
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
    public Long reserveParkingSpot(LocalDateTime startTime, LocalDateTime endTime, String reservingMember) {
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
        var hasActiveReservation = parkingReservationRepository.hasActiveReservation(startTime, endTime, reservingMember);

        if (hasActiveReservation) {
            throw new ActiveReservationExistsException();
        }

        // Find any available spot
        var spot = parkingSpotRepository.findAnyAvailableSpot()
                .orElseThrow(NoAvailableSpotsException::new);

        // Create and save the reservation
        return parkingReservationRepository.storeReservation(startTime, endTime, reservingMember, spot);
    }

}

