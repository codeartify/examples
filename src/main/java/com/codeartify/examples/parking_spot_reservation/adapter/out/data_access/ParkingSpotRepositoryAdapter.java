package com.codeartify.examples.parking_spot_reservation.adapter.out.data_access;

import com.codeartify.examples.parking_spot_reservation.adapter.out.data_access.model.ParkingSpotDBEntity;
import com.codeartify.examples.parking_spot_reservation.domain.value.ParkingSpotId;
import com.codeartify.examples.parking_spot_reservation.application.port.out.ForFindingAvailableParkingSpots;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ParkingSpotRepositoryAdapter implements ForFindingAvailableParkingSpots {
    private final ParkingSpotDBEntityRepository parkingSpotDBEntityRepository;

    @Override
    public Optional<ParkingSpotId> findAnyAvailableSpot() {
        ParkingSpotDBEntity spot = parkingSpotDBEntityRepository.findAnyAvailableSpot();

        Optional<ParkingSpotId> spotOptional = Optional.empty();
        if(spot != null) {
            spotOptional = Optional.of(new ParkingSpotId(spot.getId()));
        }
        return spotOptional;
    }
}
