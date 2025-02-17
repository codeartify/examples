package com.codeartify.examples.parking_spot_reservation.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class ParkingSpotReservationService implements ForReservingParkingSpots {
    private final ForCheckingActiveReservations forCheckingActiveReservations;
    private final ForStoringReservations forStoringReservations;
    private final ForFindingAvailableParkingSpots forFindingAvailableParkingSpots;

    private static final LocalTime OPENING_TIME = LocalTime.of(6, 0); // 6:00 AM
    private static final LocalTime CLOSING_TIME = LocalTime.of(22, 0); // 10:00 PM

    @Transactional
    @Override
    public ReservationId reserveParkingSpot(ReservationDetails reservationDetails) {
        // Validate reservation duration
        if (Duration.between(reservationDetails.reservationPeriod().startTime(), reservationDetails.reservationPeriod().endTime()).toMinutes() < 30) {
            throw new ReservationShorterThan30MinutesException();
        }
        // Ensure the end time is after the start time
        if (reservationDetails.reservationPeriod().endTime().isBefore(reservationDetails.reservationPeriod().startTime())) {
            throw new EndTimeMustBeAfterStartTimeException();
        }
        // Ensure reservation is within operating hours
        if (reservationDetails.reservationPeriod().startTime().toLocalTime().isBefore(OPENING_TIME) || reservationDetails.reservationPeriod().endTime().toLocalTime().isAfter(CLOSING_TIME)) {
            throw new ReservationOutsideOperatingHoursException();
        }
        // Check if the user already has an active reservation
        var hasActiveReservation = forCheckingActiveReservations.hasActiveReservation(reservationDetails);

        if (hasActiveReservation) {
            throw new ActiveReservationExistsException();
        }

        // Find any available spot
        var spot = forFindingAvailableParkingSpots.findAnyAvailableSpot()
                .orElseThrow(NoAvailableSpotsException::new);

        // Create and save the reservation
        var reservation = new Reservation(reservationDetails, spot);

        return forStoringReservations.storeReservation(reservation);
    }

}

