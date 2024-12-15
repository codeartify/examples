package com.codeartify.examples.parking_spot_reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingReservationRequest {
    private String reservedBy;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
