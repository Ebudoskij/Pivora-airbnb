package org.ebudoskyi.houserent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PropertyCreationDTO {
    @NotBlank(message = "Title shouldn`t be empty or null")
    private String title;
    private String description;
    @NotNull(message = "City is mandatory")
    private String city;
    private String location;
    @NotNull(message = "price is mandatory")
    private double pricePerNight;
}
