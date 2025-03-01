package com.codeartify.examples.parking_spot_reservation.service;

import com.codeartify.examples.parking_spot_reservation.adapter.data_access.ParkingReservationRepository;
import com.codeartify.examples.parking_spot_reservation.adapter.data_access.ParkingSpotRepositoryAdapter;
import com.codeartify.examples.parking_spot_reservation.controller.ParkingSpotReservationController;
import com.codeartify.examples.parking_spot_reservation.dto.ParkingReservationRequest;
import com.codeartify.examples.parking_spot_reservation.model.ParkingSpotDbEntity;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingReservationDbEntityRepository;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingSpotDbEntityRepository;
import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ParkingSpotDbEntityReservationServiceApprovalTestShould {

    @Test
    void test() { 
        CombinationApprovals.verifyAllCombinations(
            ParkingSpotDbEntityReservationServiceApprovalTestShould::method,
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
            new ParkingReservationDbEntityRepository[]{withActiveReservation(), withoutActiveReservation()},
            new ParkingSpotDbEntityRepository[]{findNoSpot(), findSpot()}
        );
    }

    private ParkingSpotDbEntityRepository findSpot() {
        var mock = mock(ParkingSpotDbEntityRepository.class);
        when(mock.findAnyAvailableSpot()).thenReturn(new ParkingSpotDbEntity(true));
        when(mock.toString()).thenReturn("");
        return mock;
    }

    private static ParkingSpotDbEntityRepository findNoSpot() {
        var mock = mock(ParkingSpotDbEntityRepository.class);
        when(mock.findAnyAvailableSpot()).thenReturn(null);
        when(mock.toString()).thenReturn("");
        return mock;
    }

    private static ParkingReservationDbEntityRepository withoutActiveReservation() {
        var mock = mock(ParkingReservationDbEntityRepository.class);
        when(mock.hasActiveReservation(any(), any(), any())).thenReturn(false);
        when(mock.toString()).thenReturn("");
        return mock;
    }

    private static ParkingReservationDbEntityRepository withActiveReservation() {
        var mock = mock(ParkingReservationDbEntityRepository.class);
        when(mock.hasActiveReservation(any(), any(), any())).thenReturn(true);
        when(mock.toString()).thenReturn("");
        return mock;
    }

    private static String method(ParkingReservationRequest request, ParkingReservationDbEntityRepository parkingReservationRepository, ParkingSpotDbEntityRepository parkingSpotDbEntityRepository) {
        var parkingReservationRepository1 = new ParkingReservationRepository(parkingReservationRepository, parkingSpotDbEntityRepository);
        var parkingSpotReservationController = new ParkingSpotReservationController(
            new ParkingSpotReservationService(parkingReservationRepository1, new ParkingSpotRepositoryAdapter(parkingSpotDbEntityRepository))
        );
        var objectResponseEntity = parkingSpotReservationController.reserveParkingSpot(request);
        return objectResponseEntity.toString();
    }
}
