package com.codeartify.examples.parking_spot_reservation.service;

import org.springframework.transaction.annotation.Transactional;


public interface ForReservingParkingSpots {
    @Transactional
    ReservationId reserveParkingSpot(ReservationDetails ReservationDetails);
}
