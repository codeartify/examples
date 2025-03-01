package com.codeartify.examples.parking_spot_reservation.service.port.in;

import com.codeartify.examples.parking_spot_reservation.service.ParkingReservationResult;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

public interface ReserveParkingSpot {
    @Transactional
    ParkingReservationResult reserveParkingSpot(LocalDateTime startTime, LocalDateTime endTime, String reservingMember);
}
