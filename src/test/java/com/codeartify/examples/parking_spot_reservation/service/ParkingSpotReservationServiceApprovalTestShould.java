package com.codeartify.examples.parking_spot_reservation.service;

import com.codeartify.examples.parking_spot_reservation.controller.ParkingSpotReservationController;
import com.codeartify.examples.parking_spot_reservation.dto.ParkingReservationRequest;
import com.codeartify.examples.parking_spot_reservation.model.ParkingSpot;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingReservationRepository;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingSpotRepository;
import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ParkingSpotReservationServiceApprovalTestShould {

    @Test
    void cover_reserve_parking_spot() { 
        CombinationApprovals.verifyAllCombinations(
            ParkingSpotReservationServiceApprovalTestShould::createAssertableOutput,
            new ParkingReservationRequest[]{
                new ParkingReservationRequest(null, LocalDateTime.of(2025, 3, 25, 8, 0), LocalDateTime.of(2025, 3, 25, 8, 31)),
                new ParkingReservationRequest(null, LocalDateTime.of(2025, 3, 25, 8, 0), LocalDateTime.of(2025, 3, 25, 22, 1)),
                new ParkingReservationRequest(null, LocalDateTime.of(2025, 3, 25, 5, 59), LocalDateTime.of(2025, 3, 25, 8, 31)),
                new ParkingReservationRequest(null, LocalDateTime.of(2025, 3, 25, 8, 0), LocalDateTime.of(2025, 3, 25, 8, 29))
            },
            new ParkingReservationRepository[]{withoutActiveReservation(), withActiveReservation()},
            new ParkingSpotRepository[]{findSpot(), findNoSpot()}
        );
    }

    private static String createAssertableOutput(ParkingReservationRequest request,
                                                 ParkingReservationRepository parkingReservationRepository,
                                                 ParkingSpotRepository parkingSpotRepository) {
        var service = new ParkingSpotReservationService(parkingReservationRepository, parkingSpotRepository);
        var controller = new ParkingSpotReservationController(service);
        var response = controller.reserveParkingSpot(request);
        return stateAsString(response);
    }

    private static String stateAsString(ResponseEntity<Object> response) {
        return "Body: " + response.getBody() +
            " status code: " + response.getStatusCode();
    }

    private static ParkingReservationRepository withoutActiveReservation() {
        var mock = mock(ParkingReservationRepository.class);
        when(mock.hasActiveReservation(any(), any(), any())).thenReturn(false);
        when(mock.toString()).thenReturn("withoutActiveReservation");
        return mock;
    }

    private ParkingSpotRepository findSpot() {
        var mock = mock(ParkingSpotRepository.class);
        when(mock.findAnyAvailableSpot()).thenReturn(new ParkingSpot(true));
        when(mock.toString()).thenReturn("findSpot");
        return mock;
    }

    private static ParkingSpotRepository findNoSpot() {
        var mock = mock(ParkingSpotRepository.class);
        when(mock.findAnyAvailableSpot()).thenReturn(null);
        when(mock.toString()).thenReturn("findNoSpot");
        return mock;
    }

    private static ParkingReservationRepository withActiveReservation() {
        var mock = mock(ParkingReservationRepository.class);
        when(mock.hasActiveReservation(any(), any(), any())).thenReturn(true);
        when(mock.toString()).thenReturn("withActiveReservation");
        return mock;
    }
}
