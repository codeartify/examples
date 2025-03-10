package com.codeartify.examples.parking_spot_reservation.application;

import com.codeartify.examples.parking_spot_reservation.application.exception.NoAvailableSpotsException;
import com.codeartify.examples.parking_spot_reservation.application.port.in.ForReservingParkingSpots;
import com.codeartify.examples.parking_spot_reservation.application.port.out.ForCheckingActiveReservations;
import com.codeartify.examples.parking_spot_reservation.application.port.out.ForFindingAvailableParkingSpots;
import com.codeartify.examples.parking_spot_reservation.application.port.out.ForStoringReservations;
import com.codeartify.examples.parking_spot_reservation.domain.entity.Reservation;
import com.codeartify.examples.parking_spot_reservation.domain.exception.ActiveReservationExistsException;
import com.codeartify.examples.parking_spot_reservation.domain.value.ReservationDetails;
import com.codeartify.examples.parking_spot_reservation.domain.value.ReservationId;
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

