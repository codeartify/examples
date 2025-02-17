package com.codeartify.examples.parking_spot_reservation.service;

import java.time.LocalDateTime;

public record ReservationPeriod(LocalDateTime startTime, LocalDateTime endTime) { }

