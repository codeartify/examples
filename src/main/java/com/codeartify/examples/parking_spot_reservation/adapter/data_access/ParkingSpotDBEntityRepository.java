package com.codeartify.examples.parking_spot_reservation.adapter.data_access;

import com.codeartify.examples.parking_spot_reservation.adapter.data_access.model.ParkingSpotDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotDBEntityRepository extends JpaRepository<ParkingSpotDBEntity, Long> {
    @Query("SELECT p FROM ParkingSpotDBEntity p WHERE p.isAvailable = true ORDER BY FUNCTION('RAND') LIMIT 1")
    ParkingSpotDBEntity findAnyAvailableSpot();
}
