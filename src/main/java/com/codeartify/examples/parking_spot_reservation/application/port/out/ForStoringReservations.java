package com.codeartify.examples.parking_spot_reservation.application.port.out;

import com.codeartify.examples.parking_spot_reservation.domain.entity.Reservation;
import com.codeartify.examples.parking_spot_reservation.domain.value.ReservationId;

public interface ForStoringReservations {
    ReservationId storeReservation(Reservation reservation);
}

