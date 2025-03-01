package com.codeartify.examples.parking_spot_reservation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ParkingReservationDbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reservedBy;

    private Long spotId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public ParkingReservationDbEntity(String reservedBy, Long spotId, LocalDateTime startTime, LocalDateTime endTime) {
        this.reservedBy = reservedBy;
        this.spotId = spotId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ParkingReservationDbEntity() {

    }
}
