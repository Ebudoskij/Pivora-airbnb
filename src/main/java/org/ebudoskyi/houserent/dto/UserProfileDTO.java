package org.ebudoskyi.houserent.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private String name;
    private String email;
    private String profileImage;
    private int propertyCount;
    private int bookingCount;
    private int reviewCount;
    public UserProfileDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
