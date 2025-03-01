package com.codeartify.examples.parking_spot_reservation.controller;

import com.codeartify.examples.parking_spot_reservation.dto.ParkingReservationRequest;
import com.codeartify.examples.parking_spot_reservation.dto.ParkingReservationResponse;
import com.codeartify.examples.parking_spot_reservation.service.*;
import com.codeartify.examples.parking_spot_reservation.service.port.in.ReserveParkingSpot;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ParkingSpotReservationController {

    private final ReserveParkingSpot reserveParkingSpot;


    @PostMapping("/reserveSpot")
    public ResponseEntity<Object> reserveParkingSpot(@RequestBody ParkingReservationRequest request) {
        try {
            var startTime = request.getStartTime();
            var endTime = request.getEndTime();
            var reservingMember = request.getReservedBy();

            var result = reserveParkingSpot.reserveParkingSpot(startTime, endTime, reservingMember);
            
            var response = new ParkingReservationResponse();
            response.setReservationId(result.id());
            response.setReservedBy(result.reservingMember());
            response.setStartTime(result.startTime());
            response.setEndTime(result.endTime());
 
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return presentFailure(e);
        }
    }

    private static ResponseEntity<Object> presentFailure(Exception e) {
        if (e instanceof ReservationTimeShorterThanMinimalTime) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Reservation must be at least 30 minutes long.");
        }
        if (e instanceof EndTimeBeforeStartTimeException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("End time must be after start time.");
        }
        if (e instanceof ReservationOutsideOperatingTimeException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Reservations can only be made between 6:00 AM and 10:00 PM.");
        }
        if (e instanceof ActiveReservationException) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("You already have an active reservation.");
        }
        if(e instanceof NoAvailableSpotLeftException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No available spot left.");
        }
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
