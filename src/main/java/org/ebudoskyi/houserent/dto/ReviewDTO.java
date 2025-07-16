package org.ebudoskyi.houserent.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Long id;
    private Long authorId;
    private Long propertyId;
    private int rating;
    private String content;
    private LocalDate date;
}