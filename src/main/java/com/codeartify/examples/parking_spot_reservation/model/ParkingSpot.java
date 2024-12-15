package com.codeartify.examples.parking_spot_reservation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
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
