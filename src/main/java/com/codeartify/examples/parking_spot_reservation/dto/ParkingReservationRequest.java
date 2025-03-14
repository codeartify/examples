package com.codeartify.examples.parking_spot_reservation.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingReservationRequest {
    private String reservedBy;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public String toString() {
        return "Request[" + reservedBy + "," + startTime.toString() + "-" + endTime.toString() + "]";
    }
}
