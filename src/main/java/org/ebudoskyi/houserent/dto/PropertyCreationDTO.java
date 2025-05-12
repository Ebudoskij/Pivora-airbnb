package org.ebudoskyi.houserent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyCreationDTO {
    @NotBlank(message = "Title shouldn`t be empty or null")
    private String title;
    private String description;
    @NotNull(message = "City is mandatory")
    private String city;
    private String location;
    @NotNull
    @Positive
    private int rooms;
    @NotNull(message = "price is mandatory")
    private double pricePerNight;
}
