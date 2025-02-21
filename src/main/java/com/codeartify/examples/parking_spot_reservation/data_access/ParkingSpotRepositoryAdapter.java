package com.codeartify.examples.parking_spot_reservation.data_access;

import com.codeartify.examples.parking_spot_reservation.model.ParkingSpotDBEntity;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingSpotDBEntityRepository;
import com.codeartify.examples.parking_spot_reservation.service.ParkingSpotId;
import com.codeartify.examples.parking_spot_reservation.service.ForFindingAvailableParkingSpots;
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
