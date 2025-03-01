package com.codeartify.examples.parking_spot_reservation.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Getter 
@EqualsAndHashCode
@ToString
public final class ReservationPeriod {

    private static final LocalTime OPENING_TIME = LocalTime.of(6, 0); // 6:00 AM
    private static final LocalTime CLOSING_TIME = LocalTime.of(22, 0); // 10:00 PM
    
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private ReservationPeriod(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static ReservationPeriod create(LocalDateTime startTime, LocalDateTime endTime) {
        if (isShorterThanMinimalReservationPeriod(startTime, endTime)) {
            throw new ReservationTimeShorterThanMinimalTime();
        }
        
        if (endTime.isBefore(startTime)) {
            throw new EndTimeBeforeStartTimeException();
        }
        
        if (isWithinOperatingHours(startTime, endTime)) {
            throw new ReservationOutsideOperatingTimeException();
        }
        
        return new ReservationPeriod(startTime, endTime);
    }

    private static boolean isWithinOperatingHours(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime.toLocalTime().isBefore(OPENING_TIME)
            || endTime.toLocalTime().isAfter(CLOSING_TIME);
    }

    private static boolean isShorterThanMinimalReservationPeriod(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime).toMinutes() < 30;
    }


}
