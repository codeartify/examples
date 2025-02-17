package com.codeartify.examples.parking_spot_reservation.controller;

import com.codeartify.examples.parking_spot_reservation.dto.ParkingReservationRequest;
import com.codeartify.examples.parking_spot_reservation.dto.ParkingReservationResponse;
import com.codeartify.examples.parking_spot_reservation.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ParkingSpotReservationController {

    private final ForReservingParkingSpots forReservingParkingSpots;

    @PostMapping("/reserveSpot")
    public ResponseEntity<Object> reserveParkingSpot(@RequestBody ParkingReservationRequest request) {
        var reservingMember = request.getReservedBy();
        var startTime = request.getStartTime();
        var endTime = request.getEndTime();

        ReservationId reservationId = null;
        try {
            var reservationPeriod = new ReservationPeriod(startTime, endTime);
            var member = new ReservingMember(reservingMember);
            var reservationDetails = new ReservationDetails(reservationPeriod, member);
            reservationId = forReservingParkingSpots.reserveParkingSpot(reservationDetails);
        } catch (RuntimeException e) {
            if (e instanceof ReservationShorterThan30MinutesException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Reservation must be at least 30 minutes long.");
            }
            if (e instanceof EndTimeMustBeAfterStartTimeException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("End time must be after start time.");
            }
            if (e instanceof ReservationOutsideOperatingHoursException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Reservations can only be made between 6:00 AM and 10:00 PM.");
            }
            if (e instanceof ActiveReservationExistsException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You already have an active reservation.");
            }
            if (e instanceof NoAvailableSpotsException) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No available spot left.");
            }
        }

        // Build and return the response
        var response = new ParkingReservationResponse();
        response.setReservationId(reservationId.value());
        response.setReservedBy(reservingMember);
        response.setStartTime(startTime);
        response.setEndTime(endTime);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
