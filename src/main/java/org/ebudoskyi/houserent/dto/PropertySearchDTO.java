package org.ebudoskyi.houserent.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PropertySearchDTO {
    @NotNull(message = "City is mandatory")
    private String city;
    @NotNull(message = "Day of visiting is mandatory")
    @Future(message = "date must be in future!")
    private LocalDate startDate;
    @NotNull(message = "Day of leaving is mandatory")
    @Future(message = "date must be in future!")
    private LocalDate endDate;
}
