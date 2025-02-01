package com.codeartify.examples.parking_spot_reservation.service;

import java.time.LocalDateTime;

public interface ForCheckingActiveReservations {
    boolean hasActiveReservation(LocalDateTime startTime, LocalDateTime endTime, String reservingMember);
}
