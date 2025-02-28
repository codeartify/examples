package com.codeartify.examples.parking_spot_reservation.controller;

import com.codeartify.examples.parking_spot_reservation.dto.ParkingReservationRequest;
import com.codeartify.examples.parking_spot_reservation.service.ParkingSpotReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ParkingSpotReservationControllerApprovalTestShould {
    @Test
    void test() {

        var parkingSpotReservationService = new ParkingSpotReservationService(null, null);
        var unitUnderTest = new ParkingSpotReservationController(parkingSpotReservationService);
        String reservedBy = null;
        var startTime = LocalDateTime.of(2025, 2, 23, 8, 0);
        var endTime = LocalDateTime.of(2025, 2, 23, 8, 29);
        ParkingReservationRequest request = new ParkingReservationRequest(reservedBy, startTime, endTime);

        var result = unitUnderTest.reserveParkingSpot(request);

        assertEquals("Body: Reservation must be at least 30 minutes long. status code: 400 BAD_REQUEST", stateAsString(result));
    }

    private String stateAsString(ResponseEntity<Object> result) {
        return "Body: " + result.getBody() + " status code: " + result.getStatusCode();
    }

}
