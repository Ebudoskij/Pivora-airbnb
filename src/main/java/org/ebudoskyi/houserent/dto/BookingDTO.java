package org.ebudoskyi.houserent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ebudoskyi.houserent.model.BookingEnum;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDTO {
    private Long id;
    private Long propertyId;
    private Long tenantId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;
    private BookingEnum status;
}
