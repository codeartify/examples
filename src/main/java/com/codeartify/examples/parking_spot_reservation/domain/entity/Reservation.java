package com.codeartify.examples.parking_spot_reservation.domain.entity;

import com.codeartify.examples.parking_spot_reservation.domain.value.ParkingSpotId;
import com.codeartify.examples.parking_spot_reservation.domain.value.ReservationDetails;

import java.util.Objects;

public final class Reservation {
    private final ReservationDetails reservationDetails;
    private final ParkingSpotId parkingSpotId;

    private Reservation(ReservationDetails reservationDetails, ParkingSpotId parkingSpotId) {
        this.reservationDetails = reservationDetails;
        this.parkingSpotId = parkingSpotId;
    }

    public static Reservation schedule(ReservationDetails reservationDetails, ParkingSpotId parkingSpotId) {
        return new Reservation(reservationDetails, parkingSpotId);
    }

    public ReservationDetails reservationDetails() {
        return reservationDetails;
    }

    public ParkingSpotId parkingSpotId() {
        return parkingSpotId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Reservation) obj;
        return Objects.equals(this.reservationDetails, that.reservationDetails) &&
                Objects.equals(this.parkingSpotId, that.parkingSpotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationDetails, parkingSpotId);
    }

    @Override
    public String toString() {
        return "Reservation[" +
                "reservationDetails=" + reservationDetails + ", " +
                "parkingSpotId=" + parkingSpotId + ']';
    }
}

