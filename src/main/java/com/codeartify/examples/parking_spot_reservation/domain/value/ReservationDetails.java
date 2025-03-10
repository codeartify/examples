package com.codeartify.examples.parking_spot_reservation.domain.value;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ReservationDetails {
    private final ReservationPeriod reservationPeriod;
    private final ReservingMember reservingMember;

    private ReservationDetails(ReservationPeriod reservationPeriod, ReservingMember reservingMember) {
        this.reservationPeriod = reservationPeriod;
        this.reservingMember = reservingMember;
    }

    public static ReservationDetails create(LocalDateTime startTime, LocalDateTime endTime, String reservingMember) {
        var reservationPeriod = ReservationPeriod.create(startTime, endTime);
        var member = new ReservingMember(reservingMember);
        return new ReservationDetails(reservationPeriod, member);
    }

    public ReservationPeriod reservationPeriod() {
        return reservationPeriod;
    }

    public ReservingMember reservingMember() {
        return reservingMember;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ReservationDetails) obj;
        return Objects.equals(this.reservationPeriod, that.reservationPeriod) &&
                Objects.equals(this.reservingMember, that.reservingMember);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationPeriod, reservingMember);
    }

    @Override
    public String toString() {
        return "ReservationDetails[" +
                "reservationPeriod=" + reservationPeriod + ", " +
                "reservingMember=" + reservingMember + ']';
    }

}

