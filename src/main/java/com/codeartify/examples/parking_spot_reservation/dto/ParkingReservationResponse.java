package com.codeartify.examples.parking_spot_reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingReservationResponse {
    private Long reservationId;
    private Long spotId;
    private String reservedBy;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
