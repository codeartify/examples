package com.codeartify.examples.parking_spot_reservation.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "parking_spot")
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isAvailable;

    public ParkingSpot(boolean isAvailable ) {
        this.isAvailable = isAvailable;
    }

    public ParkingSpot() {
    }
}
