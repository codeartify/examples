package com.codeartify.examples.parking_spot_reservation.controller;

import com.codeartify.examples.parking_spot_reservation.dto.ParkingReservationRequest;
import com.codeartify.examples.parking_spot_reservation.service.ParkingSpotReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ParkingSpotReservationController {

    private final ParkingSpotReservationService parkingSpotReservationService;


    @PostMapping("/reserveSpot")
    public ResponseEntity<Object> reserveParkingSpot(@RequestBody ParkingReservationRequest request) {
        return parkingSpotReservationService.reserveParkingSpot(request);
    }

}
