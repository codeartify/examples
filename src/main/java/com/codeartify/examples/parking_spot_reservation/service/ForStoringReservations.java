package com.codeartify.examples.parking_spot_reservation.service;

import java.time.LocalDateTime;

public interface ForStoringReservations {
    Long storeReservation(LocalDateTime startTime, LocalDateTime endTime, String reservingMember, ParkingSpot spot);
}
