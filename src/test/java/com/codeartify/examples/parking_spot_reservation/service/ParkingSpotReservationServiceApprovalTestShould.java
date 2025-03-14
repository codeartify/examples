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
    void cover_happy_path() { 
        CombinationApprovals.verifyAllCombinations(
            ParkingSpotReservationServiceApprovalTestShould::createAssertableOutput,
                new ParkingReservationRequest[]{
                    new ParkingReservationRequest(null,
                        LocalDateTime.of(2020, 1, 1, 8, 0),
                        LocalDateTime.of(2020, 1, 1, 8, 31)),
                },
            new ParkingReservationRepository[]{withoutActiveReservation()},
            new ParkingSpotRepository[]{findSpot()}
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
