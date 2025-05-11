package org.ebudoskyi.houserent.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PropertyDTO {
    private Long id;
    private String title;
    private String description;
    private String location;
    private double pricePerNight;
    private int rooms;
    private Long ownerId;
    private List<Long> availabilitiesIds = new ArrayList<>();
    private List<Long>  bookingsIds = new ArrayList<>();
    private List<Long>  reviewsIds = new ArrayList<>();
    public PropertyDTO(Long id, String title, String description, String location, double pricePerNight, int rooms,  Long ownerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.rooms = rooms;
        this.ownerId = ownerId;
    }
}
