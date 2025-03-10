package com.codeartify.examples.parking_spot_reservation.application.port.in;

import com.codeartify.examples.parking_spot_reservation.domain.value.ReservationDetails;
import com.codeartify.examples.parking_spot_reservation.domain.value.ReservationId;
import org.springframework.transaction.annotation.Transactional;


public interface ForReservingParkingSpots {
    @Transactional
    ReservationId reserveParkingSpot(ReservationDetails ReservationDetails);
}
