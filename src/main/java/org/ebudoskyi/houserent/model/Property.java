package org.ebudoskyi.houserent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "property_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title shouldn`t bu empty or null")
    @Column(nullable = false)
    private String title;

    private String description;

    @NotBlank
    @Column(nullable = false)
    private String city;

    @NotBlank
    @Column(nullable = false)
    private String location;

    @Positive(message = "Price should be a positive number")
    @Column(nullable = false)
    private double pricePerNight;

    @Positive(message = "Amount of rooms should be a positive number")
    @Column(nullable = false)
    private int rooms;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "property")
    private List<Booking>  bookings = new ArrayList<>();

    @OneToMany(mappedBy = "property")
    private List<Review>  reviews = new ArrayList<>();
}
