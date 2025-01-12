package com.codeartify.examples.parking_spot_reservation.repository;

import com.codeartify.examples.parking_spot_reservation.model.ParkingSpotDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotDBEntityRepository extends JpaRepository<ParkingSpotDBEntity, Long> {
    @Query("SELECT p FROM ParkingSpotDBEntity p WHERE p.isAvailable = true ORDER BY FUNCTION('RAND') LIMIT 1")
    ParkingSpotDBEntity findAnyAvailableSpot();
}
