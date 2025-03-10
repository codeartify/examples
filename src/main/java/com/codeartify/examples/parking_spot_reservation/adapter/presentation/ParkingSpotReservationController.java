package com.codeartify.examples.parking_spot_reservation.adapter.presentation;

import com.codeartify.examples.parking_spot_reservation.adapter.presentation.dto.ParkingReservationRequest;
import com.codeartify.examples.parking_spot_reservation.adapter.presentation.dto.ParkingReservationResponse;
import com.codeartify.examples.parking_spot_reservation.application.exception.NoAvailableSpotsException;
import com.codeartify.examples.parking_spot_reservation.application.port.in.ForReservingParkingSpots;
import com.codeartify.examples.parking_spot_reservation.domain.exception.ActiveReservationExistsException;
import com.codeartify.examples.parking_spot_reservation.domain.exception.EndTimeMustBeAfterStartTimeException;
import com.codeartify.examples.parking_spot_reservation.domain.exception.ReservationOutsideOperatingHoursException;
import com.codeartify.examples.parking_spot_reservation.domain.exception.ReservationShorterThanMinimalReservationDurationException;
import com.codeartify.examples.parking_spot_reservation.domain.value.ReservationDetails;
import com.codeartify.examples.parking_spot_reservation.domain.value.ReservationId;
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
            var reservationDetails = ReservationDetails.create(startTime, endTime, reservingMember);
            reservationId = forReservingParkingSpots.reserveParkingSpot(reservationDetails);
        } catch (RuntimeException e) {
            if (e instanceof ReservationShorterThanMinimalReservationDurationException) {
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
