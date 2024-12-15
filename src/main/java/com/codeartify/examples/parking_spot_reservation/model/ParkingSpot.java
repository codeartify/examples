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

    private boolean hasEVCharging;


    public ParkingSpot(boolean isAvailable, boolean hasEVCharging) {
        this.isAvailable = isAvailable;
        this.hasEVCharging = hasEVCharging;
    }

    public ParkingSpot() {
    }
}
