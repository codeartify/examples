package com.codeartify.examples.parking_spot_reservation.service.port.out;

import com.codeartify.examples.parking_spot_reservation.service.ParkingReservation;

public interface StoreParkingReservation {
    ParkingReservation storeParkingReservation(ParkingReservation parkingReservation);
}
