package com.codeartify.examples.parking_spot_reservation.service.port.out;

import com.codeartify.examples.parking_spot_reservation.service.ReservationPeriod;

public interface CheckActiveReservations {
    boolean hasActiveReservation(ReservationPeriod reservationPeriod, String reservingMember);
}
