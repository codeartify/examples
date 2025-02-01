package com.codeartify.examples.parking_spot_reservation.service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface ForReservingParkingSpots {
    @Transactional
    Long reserveParkingSpot(LocalDateTime startTime, LocalDateTime endTime, String reservingMember);
}
