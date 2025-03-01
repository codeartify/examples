package com.codeartify.examples.parking_spot_reservation.service;
import com.codeartify.examples.parking_spot_reservation.adapter.data_access.ParkingReservationRepository;
import com.codeartify.examples.parking_spot_reservation.service.port.out.FindAnyAvailableSpot;
import com.codeartify.examples.parking_spot_reservation.service.port.in.ReserveParkingSpot;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ParkingSpotReservationService implements ReserveParkingSpot {
    private final ParkingReservationRepository parkingReservationRepository;
    private final FindAnyAvailableSpot parkingSpotRepository;

 
    @Transactional
    @Override
    public ParkingReservationResult reserveParkingSpot(LocalDateTime startTime, LocalDateTime endTime, String reservingMember) {
 
        // Check if the user already has an active reservation
        var reservationPeriod = ReservationPeriod.create(startTime, endTime);
        var hasActiveReservation = parkingReservationRepository.hasActiveReservation(reservationPeriod, reservingMember);

        if (hasActiveReservation) {
            throw new ActiveReservationException();
        }

        // Find any available spot
        var parkingSpotId = parkingSpotRepository.findAnyAvailableSpot();

        var parkingReservation = new ParkingReservation(parkingSpotId, reservingMember, reservationPeriod);

        var storedReservation = parkingReservationRepository.storeParkingReservation(parkingReservation);

        // Build and return the response
        return new ParkingReservationResult(storedReservation.getId(),
                storedReservation.reservingMember(),
                storedReservation.startTime(),
                storedReservation.endTime());
    }

}

