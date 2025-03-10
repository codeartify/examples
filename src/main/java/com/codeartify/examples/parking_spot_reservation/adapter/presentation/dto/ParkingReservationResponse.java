package com.codeartify.examples.parking_spot_reservation.adapter.presentation.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ParkingReservationResponse {
    private Long reservationId;
    private Long spotId;
    private String reservedBy;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
