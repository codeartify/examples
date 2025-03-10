package com.codeartify.examples.parking_spot_reservation.adapter.data_access;

import com.codeartify.examples.parking_spot_reservation.adapter.data_access.model.ParkingReservationDBEntity;
import com.codeartify.examples.parking_spot_reservation.application.port.out.ForCheckingActiveReservations;
import com.codeartify.examples.parking_spot_reservation.application.port.out.ForStoringReservations;
import com.codeartify.examples.parking_spot_reservation.domain.entity.Reservation;
import com.codeartify.examples.parking_spot_reservation.domain.value.ReservationDetails;
import com.codeartify.examples.parking_spot_reservation.domain.value.ReservationId;
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
