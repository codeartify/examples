package com.codeartify.examples.parking_spot_reservation.service;

public interface ForCheckingActiveReservations {
    boolean hasActiveReservation(ReservationDetails reservationDetails);
}
