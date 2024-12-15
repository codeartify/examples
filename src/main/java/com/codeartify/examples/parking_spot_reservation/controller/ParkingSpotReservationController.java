package com.codeartify.examples.parking_spot_reservation.controller;

import com.codeartify.examples.parking_spot_reservation.dto.ParkingReservationRequest;
import com.codeartify.examples.parking_spot_reservation.model.ParkingReservation;
import com.codeartify.examples.parking_spot_reservation.model.ParkingSpot;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingReservationRepository;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingSpotRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpotReservationController {

    private ParkingReservationRepository parkingReservationRepository;
    private ParkingSpotRepository parkingSpotRepository;


    @PostMapping("/reserveSpot")
    public ResponseEntity<Object> reserveParkingSpot(@RequestBody ParkingReservationRequest request) {
        ParkingSpot spot;
        if (request.getSpotId() == null) {
            spot = parkingSpotRepository.findRandomAvailableSpot();
        } else {
            spot = parkingSpotRepository.findById(request.getSpotId()).orElse(null);
        }

        if (spot == null || !spot.isAvailable()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Spot not available.");
        }

        if (!request.isNeedsEVCharging() || spot.isHasEVCharging()) {
            ParkingReservation reservation = new ParkingReservation(
                    request.getReservedBy(),
                    spot.getId(),
                    request.getStartTime(),
                    request.getEndTime());

            parkingReservationRepository.save(reservation);
            spot.setAvailable(false);
            parkingSpotRepository.save(spot);
            return ResponseEntity.status(HttpStatus.CREATED).body("Spot reserved.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Spot does not meet requirements.");
    }
}
