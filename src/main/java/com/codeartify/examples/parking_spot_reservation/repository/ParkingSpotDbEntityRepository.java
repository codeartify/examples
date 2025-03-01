package com.codeartify.examples.parking_spot_reservation.repository;

import com.codeartify.examples.parking_spot_reservation.model.ParkingSpotDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotDbEntityRepository extends JpaRepository<ParkingSpotDbEntity, Long> {
    @Query("SELECT p FROM ParkingSpotDbEntity p WHERE p.isAvailable = true ORDER BY FUNCTION('RAND') LIMIT 1")
    ParkingSpotDbEntity findAnyAvailableSpot();
}
