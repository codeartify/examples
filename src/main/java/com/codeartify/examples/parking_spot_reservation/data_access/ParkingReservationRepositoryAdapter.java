package com.codeartify.examples.parking_spot_reservation.data_access;

import com.codeartify.examples.parking_spot_reservation.model.ParkingReservationDBEntity;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingReservationDBEntityRepository;
import com.codeartify.examples.parking_spot_reservation.repository.ParkingSpotDBEntityRepository;
import com.codeartify.examples.parking_spot_reservation.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ParkingReservationRepositoryAdapter implements ForStoringReservations, ForCheckingActiveReservations {
    private ParkingReservationDBEntityRepository parkingReservationDBEntityRepository;
    private ParkingSpotDBEntityRepository parkingSpotDBEntityRepository;

    @Override
    public ReservationId storeReservation(Reservation reservation) {
        var spotId = reservation.parkingSpotId().value();
        var reservedBy = reservation.reservationDetails().reservingMember().reservingMember();
        var startTime = reservation.reservationDetails().reservationPeriod().startTime();
        var endTime = reservation.reservationDetails().reservationPeriod().endTime();

        ParkingReservationDBEntity reservationDbEntity = new ParkingReservationDBEntity(reservedBy, spotId, startTime, endTime);
        this.parkingReservationDBEntityRepository.save(reservationDbEntity);

        // Mark the parking parkingSpotId as unavailable
        this.parkingSpotDBEntityRepository.findById(reservation.parkingSpotId().value()).ifPresent(s -> {
            s.setAvailable(false);
            this.parkingSpotDBEntityRepository.save(s);
        });

        return new ReservationId(reservationDbEntity.getId());
    }

    @Override
    public boolean hasActiveReservation(ReservationDetails reservationDetails) {
        return this.parkingReservationDBEntityRepository
                .hasActiveReservation(reservationDetails.reservingMember().reservingMember(), reservationDetails.reservationPeriod().startTime(), reservationDetails.reservationPeriod().endTime());
    }
}
