package com.codeartify.examples.parking_spot_reservation.application.port.out;

import com.codeartify.examples.parking_spot_reservation.domain.value.ParkingSpotId;

import java.util.Optional;

public interface ForFindingAvailableParkingSpots {
    Optional<ParkingSpotId> findAnyAvailableSpot();
}
