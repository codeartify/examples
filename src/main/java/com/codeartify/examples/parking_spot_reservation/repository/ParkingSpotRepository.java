package com.codeartify.examples.parking_spot_reservation.repository;

import com.codeartify.examples.parking_spot_reservation.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    @Query("SELECT p FROM ParkingSpot p WHERE p.isAvailable = true ORDER BY FUNCTION('RAND')")
    ParkingSpot findRandomAvailableSpot();
}
