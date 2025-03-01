package com.codeartify.examples.parking_spot_reservation.adapter.data_access;

import com.codeartify.examples.parking_spot_reservation.model.ParkingReservationDbEntity;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingReservationDbEntityRepository;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingSpotDbEntityRepository;
import com.codeartify.examples.parking_spot_reservation.service.ParkingReservation;
import com.codeartify.examples.parking_spot_reservation.service.ReservationPeriod;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ParkingReservationRepository {
    private final ParkingReservationDbEntityRepository parkingReservationDbEntityRepository;
    private final ParkingSpotDbEntityRepository parkingSpotDbEntityRepository;

    public boolean hasActiveReservation(ReservationPeriod reservationPeriod, String reservingMember) {
        return this.parkingReservationDbEntityRepository.hasActiveReservation(reservingMember, reservationPeriod.getStartTime(), reservationPeriod.getEndTime());
    }

    public ParkingReservation storeParkingReservation(ParkingReservation parkingReservation) {
        // Create and save the reservation
        ParkingReservationDbEntity reservationDbEntity = new ParkingReservationDbEntity(
                parkingReservation.reservingMember(),
                parkingReservation.parkingSpotId(),
                parkingReservation.startTime(),
                parkingReservation.endTime());
        this.parkingReservationDbEntityRepository.save(reservationDbEntity);

        this.parkingSpotDbEntityRepository.findById(parkingReservation.parkingSpotId())
                .ifPresent(parkingSpotDbEntity -> {
                    // Mark the parking spot as unavailable
                    parkingSpotDbEntity.setAvailable(false);
                    this.parkingSpotDbEntityRepository.save(parkingSpotDbEntity);
                });


        return new ParkingReservation(
                reservationDbEntity.getSpotId(), 
                reservationDbEntity.getReservedBy(),
            ReservationPeriod.create(reservationDbEntity.getStartTime(), reservationDbEntity.getEndTime()));
    }
}
