package com.codeartify.examples.parking_spot_reservation.service;

import java.util.Optional;

public interface ForFindingAvailableParkingSpots {
    Optional<ParkingSpotId> findAnyAvailableSpot();
}
