package com.codeartify.examples.parking_spot_reservation.service;
import com.codeartify.examples.parking_spot_reservation.adapter.data_access.ParkingReservationRepository;
import com.codeartify.examples.parking_spot_reservation.adapter.data_access.ParkingSpotRepositoryAdapter;
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
    private final ParkingSpotRepositoryAdapter parkingSpotRepository;


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
        if (startTime.toLocalTime().isBefore(OPENING_TIME)
                || endTime.toLocalTime().isAfter(CLOSING_TIME)) {
            throw new ReservationOutsideOperatingTimeException();
        }
        // Check if the user already has an active reservation
        var hasActiveReservation = parkingReservationRepository.hasActiveReservation(startTime, endTime, reservingMember);

        if (hasActiveReservation) {
            throw new ActiveReservationException();
        }

        // Find any available spot
        var parkingSpotId = parkingSpotRepository.findAnyAvailableSpot();

        var parkingReservation = new ParkingReservation(parkingSpotId, reservingMember, startTime, endTime);

        var storedReservation = parkingReservationRepository.storeParkingReservation(parkingReservation);

        // Build and return the response
        return new ParkingReservationResult(storedReservation.id(),
                storedReservation.reservingMember(),
                storedReservation.startTime(),
                storedReservation.endTime());
    }

}

