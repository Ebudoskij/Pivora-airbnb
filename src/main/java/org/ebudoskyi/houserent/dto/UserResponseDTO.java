package org.ebudoskyi.houserent.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private List<Long> ownedProperties = new ArrayList<>();
    private List<Long> bookings = new ArrayList<>();
    private List<Long> reviews = new ArrayList<>();
    private List<Long> sentMessages = new ArrayList<>();
    private List<Long> receivedMessages = new ArrayList<>();
    public UserResponseDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
