package com.codeartify.examples.parking_spot_reservation.service;

public record ParkingReservationResult(Long id, String reservingMember, java.time.LocalDateTime startTime,
                                       java.time.LocalDateTime endTime) {
}
