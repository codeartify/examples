package com.codeartify.examples.parking_spot_reservation.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ParkingReservationRequest {
    private String reservedBy;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
