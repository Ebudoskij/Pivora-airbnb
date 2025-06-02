package org.ebudoskyi.houserent.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PropertyResponseDTO {
    @NotNull
    private Long id;
    @NotBlank(message="Title is mandatory!")
    private String title;
    @Size(max = 300, message = "Description should be shorter than 300 symbols")
    private String description;
    @NotNull(message = "City is mandatory")
    private String city;
    private String location;
    @NotNull(message = "price is mandatory")
    @Positive(message="Price must be a positive number!")
    private double pricePerNight;
    @NotNull(message = "Amount of rooms is mandatory")
    @Positive(message="Amount of rooms must be a positive number!")
    private int rooms;
    private Long ownerId;
    private List<Long>  bookingsIds = new ArrayList<>();
    private List<Long>  reviewsIds = new ArrayList<>();
    public PropertyResponseDTO(
            Long id,
            String title,
            String description,
            String city,
            String location,
            double pricePerNight,
            int rooms,
            Long ownerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.city = city;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.rooms = rooms;
        this.ownerId = ownerId;
    }
}
