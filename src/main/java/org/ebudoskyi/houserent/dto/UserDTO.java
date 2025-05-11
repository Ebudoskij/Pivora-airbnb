package org.ebudoskyi.houserent.dto;

import lombok.*;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.model.Role;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private List<Long> ownedProperties = new ArrayList<>();
    private List<Long> bookings = new ArrayList<>();
    private List<Long> reviews = new ArrayList<>();
    private List<Long> sentMessages = new ArrayList<>();
    private List<Long> receivedMessages = new ArrayList<>();
    public UserDTO(Long id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
