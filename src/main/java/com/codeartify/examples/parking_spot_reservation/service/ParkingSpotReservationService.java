package com.codeartify.examples.parking_spot_reservation.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ParkingSpotReservationService implements ForReservingParkingSpots {
    private final ForCheckingActiveReservations forCheckingActiveReservations;
    private final ForStoringReservations forStoringReservations;
    private final ForFindingAvailableParkingSpots forFindingAvailableParkingSpots;

    @Transactional
    @Override
    public ReservationId reserveParkingSpot(ReservationDetails reservationDetails) {
        // Check if the user already has an active reservation
        var hasActiveReservation = forCheckingActiveReservations.hasActiveReservation(reservationDetails);

        if (hasActiveReservation) {
            throw new ActiveReservationExistsException();
        }

        // Find any available spot
        var spot = forFindingAvailableParkingSpots.findAnyAvailableSpot()
                .orElseThrow(NoAvailableSpotsException::new);

        // Create and save the reservation
        var reservation = Reservation.schedule(reservationDetails, spot);

        return forStoringReservations.storeReservation(reservation);
    }

}

