package org.ebudoskyi.houserent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @Size(max = 300, message = "Description should be shorter than 300 symbols")
    private String description;
    @NotNull(message = "City is mandatory")
    private String city;
    @NotBlank(message = "Location is mandatory")
    private String location;
    @NotNull(message = "Amount of rooms is mandatory")
    @Positive(message="Amount of rooms must be a positive number!")
    private Integer rooms;
    @NotNull(message = "price is mandatory")
    @Positive(message="Price must be a positive number!")
    private Double pricePerNight;
}
