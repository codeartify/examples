package com.codeartify.examples.parking_spot_reservation.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode 
public class ParkingReservationResponse {
    private Long reservationId;
    private Long spotId;
    private String reservedBy;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Override
    public String toString() {
        return "Response[reservationId[" + reservationId + "],spotId[" + spotId + "],reservedBy[" + reservedBy + "],startTime[" + startTime + "]-endTime[" + endTime + ']';
    }
}
