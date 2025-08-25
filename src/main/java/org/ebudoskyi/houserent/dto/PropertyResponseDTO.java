package org.ebudoskyi.houserent.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String city;
    private String location;
    private double pricePerNight;
    private String currency;
    private int rooms;
    private List<String>  images;
    private String phoneNumber;
    public PropertyResponseDTO(Long id, String title, String description, String city, String location, double pricePerNight, int rooms) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.city = city;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.rooms = rooms;
    }
}
