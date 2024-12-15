package com.codeartify.examples.parking_spot_reservation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ParkingReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reservedBy;

    private Long spotId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public ParkingReservation(String reservedBy, Long id, LocalDateTime startTime, LocalDateTime endTime) {
        this.reservedBy = reservedBy;
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ParkingReservation() {

    }
}