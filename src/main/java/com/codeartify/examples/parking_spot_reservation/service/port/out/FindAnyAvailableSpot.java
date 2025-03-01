package com.codeartify.examples.parking_spot_reservation.service.port.out;

import com.codeartify.examples.parking_spot_reservation.service.NoAvailableSpotLeftException;

public interface FindAnyAvailableSpot {
    Long findAnyAvailableSpot() throws NoAvailableSpotLeftException;
}
