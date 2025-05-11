package org.ebudoskyi.houserent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDTO {
    private Long bookingId;
    private Long propertyId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;
}
