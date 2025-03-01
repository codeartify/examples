package com.codeartify.examples.parking_spot_reservation.service;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ParkingReservation {
    private Long id;
    private final Long parkingSpotId;
    private final String reservingMember;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public ParkingReservation(Long parkingSpotId, String reservingMember, ReservationPeriod reservationPeriod) {
        this.parkingSpotId = parkingSpotId;
        this.reservingMember = reservingMember;
        this.startTime = reservationPeriod.getStartTime();
        this.endTime = reservationPeriod.getEndTime();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long parkingSpotId() {
        return parkingSpotId;
    }

    public String reservingMember() {
        return reservingMember;
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
        var that = (ParkingReservation) obj;
        return Objects.equals(this.parkingSpotId, that.parkingSpotId) &&
            Objects.equals(this.reservingMember, that.reservingMember) &&
            Objects.equals(this.startTime, that.startTime) &&
            Objects.equals(this.endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parkingSpotId, reservingMember, startTime, endTime);
    }

    @Override
    public String toString() {
        return "ParkingReservation[" +
            "parkingSpotId=" + parkingSpotId + ", " +
            "reservingMember=" + reservingMember + ", " +
            "startTime=" + startTime + ", " +
            "endTime=" + endTime + ']';
    }

}
