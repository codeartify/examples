package com.codeartify.examples.parking_spot_reservation.adapter.data_access.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "parking_spot")
public class ParkingSpotDBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isAvailable;

    public ParkingSpotDBEntity(boolean isAvailable ) {
        this.isAvailable = isAvailable;
    }

    public ParkingSpotDBEntity() {
    }
}
