package org.ebudoskyi.houserent.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PropertySearchDTO {
    @NotNull(message = "City is mandatory")
    private String city;
    @NotNull(message = "Day of visiting is mandatory")
    private String startDate;
    @NotNull(message = "Day of leaving is mandatory")
    private String endDate;
}
