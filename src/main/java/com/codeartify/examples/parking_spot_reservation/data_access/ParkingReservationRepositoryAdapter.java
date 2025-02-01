package com.codeartify.examples.parking_spot_reservation.data_access;

import com.codeartify.examples.parking_spot_reservation.model.ParkingReservationDBEntity;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingReservationDBEntityRepository;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingSpotDBEntityRepository;
import com.codeartify.examples.parking_spot_reservation.service.ForCheckingActiveReservations;
import com.codeartify.examples.parking_spot_reservation.service.ForStoringReservations;
import com.codeartify.examples.parking_spot_reservation.service.ParkingSpot;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class ParkingReservationRepositoryAdapter implements ForStoringReservations, ForCheckingActiveReservations {
    private ParkingReservationDBEntityRepository parkingReservationDBEntityRepository;
    private ParkingSpotDBEntityRepository parkingSpotDBEntityRepository;

    @Override
    public Long storeReservation(LocalDateTime startTime, LocalDateTime endTime, String reservingMember, ParkingSpot spot) {
        var spotId = spot.id();
        ParkingReservationDBEntity reservation = new ParkingReservationDBEntity(reservingMember, spotId, startTime, endTime);
        this.parkingReservationDBEntityRepository.save(reservation);

        // Mark the parking spot as unavailable
        this.parkingSpotDBEntityRepository.findById(spot.id()).ifPresent(s -> {
            s.setAvailable(false);
            this.parkingSpotDBEntityRepository.save(s);
        });

        return reservation.getId();
    }

    @Override
    public boolean hasActiveReservation(LocalDateTime startTime, LocalDateTime endTime, String reservingMember) {
        return this.parkingReservationDBEntityRepository
                .hasActiveReservation(reservingMember, startTime, endTime);
    }
}
