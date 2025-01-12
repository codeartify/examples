package com.codeartify.examples.parking_spot_reservation.data_access;

import com.codeartify.examples.parking_spot_reservation.model.ParkingSpotDBEntity;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingSpotDBEntityRepository;
import com.codeartify.examples.parking_spot_reservation.service.ParkingSpot;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ParkingSpotRepositoryAdapter {
    private final ParkingSpotDBEntityRepository parkingSpotDBEntityRepository;

    public Optional<ParkingSpot> findAnyAvailableSpot() {
        ParkingSpotDBEntity spot = parkingSpotDBEntityRepository.findAnyAvailableSpot();

        Optional<ParkingSpot> spotOptional = Optional.empty();
        if(spot != null) {
            spotOptional = Optional.of(new ParkingSpot(spot.getId()));
        }
        return spotOptional;
    }
}
