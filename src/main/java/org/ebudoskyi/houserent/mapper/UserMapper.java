package org.ebudoskyi.houserent.mapper;

import org.ebudoskyi.houserent.dto.UserLoginDTO;
import org.ebudoskyi.houserent.dto.UserRegisterDTO;
import org.ebudoskyi.houserent.dto.UserProfileDTO;
import org.ebudoskyi.houserent.model.*;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserProfileDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserProfileDTO userDTO = new UserProfileDTO(user.getName(), user.getEmail());
        if (user.getProfileImage() != null) {
            userDTO.setProfileImage(Base64.getEncoder().encodeToString(user.getProfileImage().getData()));
        }
        if (user.getOwnedProperties() != null) {
            int propertyCount = user.getOwnedProperties().size();
            userDTO.setPropertyCount(propertyCount);
        }
        if (user.getBookings() != null) {
            int bookingCount = user.getBookings().size();
            userDTO.setBookingCount(bookingCount);
        }
        if (user.getReviews() != null) {
            int reviewCount = user.getReviews().size();
            userDTO.setReviewCount(reviewCount);
        }
        return userDTO;
    }

    public User toEntity(UserProfileDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    public User toEntity(UserRegisterDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public User toEntity(UserLoginDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }



    public List<UserProfileDTO> toDTOList(List<User> users) {
        if (users == null) {
            return null;
        }

        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<User>  toEntityList(List<UserProfileDTO> userDTOs) {
        if (userDTOs == null) {
            return null;
        }
        return userDTOs.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
