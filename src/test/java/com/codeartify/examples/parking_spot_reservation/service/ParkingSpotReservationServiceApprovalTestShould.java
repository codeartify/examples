package com.codeartify.examples.parking_spot_reservation.service;

import com.codeartify.examples.parking_spot_reservation.controller.ParkingSpotReservationController;
import com.codeartify.examples.parking_spot_reservation.dto.ParkingReservationRequest;
import com.codeartify.examples.parking_spot_reservation.model.ParkingSpot;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingReservationRepository;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingSpotRepository;
import org.approvaltests.combinations.CombinationApprovals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.AutoApproveReporter;
import org.approvaltests.reporters.FileCaptureReporter;
import org.approvaltests.reporters.FileLauncherReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ParkingSpotReservationServiceApprovalTestShould {

    @Test
    void test() { 
        CombinationApprovals.verifyAllCombinations(
                ParkingSpotReservationServiceApprovalTestShould::method,
                new ParkingReservationRequest[]{
                        new ParkingReservationRequest(),
                        new ParkingReservationRequest("Olly", null, null),
                        new ParkingReservationRequest("Olly", LocalDateTime.of(2020, 1, 1, 1, 1), null),
                        new ParkingReservationRequest("Olly", null, LocalDateTime.of(2020, 1, 1, 8, 1)),
                        new ParkingReservationRequest(null, LocalDateTime.of(2020, 1, 1, 8, 0), LocalDateTime.of(2020, 1, 1, 8, 1)),
                        new ParkingReservationRequest("Olly", LocalDateTime.of(2020, 1, 1, 19, 5), LocalDateTime.of(2020, 1, 1, 8, 10)),
                        new ParkingReservationRequest("Olly", LocalDateTime.of(2020, 1, 1, 8, 0), LocalDateTime.of(2020, 1, 1, 8, 1)),
                        new ParkingReservationRequest("Olly", LocalDateTime.of(2020, 1, 1, 19, 0), LocalDateTime.of(2020, 1, 1, 22, 1)),
                        new ParkingReservationRequest("Olly", LocalDateTime.of(2020, 1, 1, 8, 0), LocalDateTime.of(2020, 1, 1, 8, 30)),
                        new ParkingReservationRequest("Olly", LocalDateTime.of(2020, 1, 1, 8, 0), LocalDateTime.of(2020, 1, 1, 8, 31)),
                },
                new ParkingReservationRepository[]{withActiveReservation(), withoutActiveReservation()},
                new ParkingSpotRepository[]{findNoSpot(), findSpot()}
        );
    }

    private ParkingSpotRepository findSpot() {
        var mock = mock(ParkingSpotRepository.class);
        when(mock.findAnyAvailableSpot()).thenReturn(new ParkingSpot(true));
        when(mock.toString()).thenReturn("");
        return mock;
    }

    private static ParkingSpotRepository findNoSpot() {
        var mock = mock(ParkingSpotRepository.class);
        when(mock.findAnyAvailableSpot()).thenReturn(null);
        when(mock.toString()).thenReturn("");
        return mock;
    }

    private static ParkingReservationRepository withoutActiveReservation() {
        var mock = mock(ParkingReservationRepository.class);
        when(mock.hasActiveReservation(any(), any(), any())).thenReturn(false);
        when(mock.toString()).thenReturn("");
        return mock;
    }

    private static ParkingReservationRepository withActiveReservation() {
        var mock = mock(ParkingReservationRepository.class);
        when(mock.hasActiveReservation(any(), any(), any())).thenReturn(true);
        when(mock.toString()).thenReturn("");
        return mock;
    }

    private static String method(ParkingReservationRequest request, ParkingReservationRepository parkingReservationRepository, ParkingSpotRepository parkingSpotRepository) {
        var parkingSpotReservationController = new ParkingSpotReservationController(
                new ParkingSpotReservationService(parkingReservationRepository, parkingSpotRepository)
        );
        var objectResponseEntity = parkingSpotReservationController.reserveParkingSpot(request);
        return objectResponseEntity.toString();
    }
}
