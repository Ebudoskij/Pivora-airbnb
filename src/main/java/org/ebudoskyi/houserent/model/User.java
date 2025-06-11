package org.ebudoskyi.houserent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name shouldn`t be empty or null")
    @Size(min=2, message = "Name should consist of at least 2 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Size(max = 255)
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(min=8)
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Property> ownedProperties = new ArrayList<>();

    @OneToMany(mappedBy = "tenant")
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "fromUser")
    private List<Message> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "toUser")
    private List<Message> receivedMessages = new ArrayList<>();
}
