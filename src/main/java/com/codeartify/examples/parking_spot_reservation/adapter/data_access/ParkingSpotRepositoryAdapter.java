package com.codeartify.examples.parking_spot_reservation.adapter.data_access;

import com.codeartify.examples.parking_spot_reservation.model.ParkingSpotDbEntity;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingSpotDbEntityRepository;
import com.codeartify.examples.parking_spot_reservation.service.NoAvailableSpotLeftException;
import com.codeartify.examples.parking_spot_reservation.service.port.out.FindAnyAvailableSpot;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ParkingSpotRepositoryAdapter implements FindAnyAvailableSpot {
    private final ParkingSpotDbEntityRepository parkingSpotDbEntityRepository;

    @Override
    public Long findAnyAvailableSpot() throws NoAvailableSpotLeftException {
        ParkingSpotDbEntity spot = parkingSpotDbEntityRepository.findAnyAvailableSpot();

        if (spot == null) {
            throw new NoAvailableSpotLeftException();
        }

        return spot.getId();
    }
}
