package com.codeartify.examples.parking_spot_reservation.adapter.data_access.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "parking_reservation")
public class ParkingReservationDBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reservedBy;

    private Long spotId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public ParkingReservationDBEntity(String reservedBy, Long spotId, LocalDateTime startTime, LocalDateTime endTime) {
        this.reservedBy = reservedBy;
        this.spotId = spotId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ParkingReservationDBEntity() {

    }
}
