package com.codeartify.examples.parking_spot_reservation.domain.value;

import com.codeartify.examples.parking_spot_reservation.domain.exception.EndTimeMustBeAfterStartTimeException;
import com.codeartify.examples.parking_spot_reservation.domain.exception.ReservationOutsideOperatingHoursException;
import com.codeartify.examples.parking_spot_reservation.domain.exception.ReservationShorterThanMinimalReservationDurationException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public final class ReservationPeriod {
    private static final LocalTime OPENING_TIME = LocalTime.of(6, 0); // 6:00 AM
    private static final LocalTime CLOSING_TIME = LocalTime.of(22, 0); // 10:00 PM
    private static final int MINIMAL_RESERVATION_DURATION = 30;

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private ReservationPeriod(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static ReservationPeriod create(LocalDateTime startTime, LocalDateTime endTime) {
        if (shorterThanMinimalReservationDuration(startTime, endTime)) {
            throw new ReservationShorterThanMinimalReservationDurationException();
        }
        if (endTime.isBefore(startTime)) {
            throw new EndTimeMustBeAfterStartTimeException();
        }
        if (outsideOperatingHours(startTime, endTime)) {
            throw new ReservationOutsideOperatingHoursException();
        }
        return new ReservationPeriod(startTime, endTime);
    }

    private static boolean shorterThanMinimalReservationDuration(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime).toMinutes() < MINIMAL_RESERVATION_DURATION;
    }

    private static boolean outsideOperatingHours(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime.toLocalTime().isBefore(OPENING_TIME) || endTime.toLocalTime().isAfter(CLOSING_TIME);
    }

    public LocalDateTime startTime() {
        return startTime;
    }

    public LocalDateTime endTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ReservationPeriod) obj;
        return Objects.equals(this.startTime, that.startTime) &&
                Objects.equals(this.endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }

    @Override
    public String toString() {
        return "ReservationPeriod[" +
                "startTime=" + startTime + ", " +
                "endTime=" + endTime + ']';
    }
}

