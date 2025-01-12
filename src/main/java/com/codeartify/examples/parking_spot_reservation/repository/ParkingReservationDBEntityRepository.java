package com.codeartify.examples.parking_spot_reservation.repository;

import com.codeartify.examples.parking_spot_reservation.model.ParkingReservationDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ParkingReservationDBEntityRepository extends JpaRepository<ParkingReservationDBEntity, Long> {
    @Query("SELECT COUNT(r) > 0 FROM ParkingReservationDBEntity r " +
            "WHERE r.reservedBy = :reservedBy " +
            "AND r.startTime < :endTime " +
            "AND r.endTime > :startTime")
    boolean hasActiveReservation(@Param("reservedBy") String reservedBy,
                                 @Param("startTime") LocalDateTime startTime,
                                 @Param("endTime") LocalDateTime endTime);
}


