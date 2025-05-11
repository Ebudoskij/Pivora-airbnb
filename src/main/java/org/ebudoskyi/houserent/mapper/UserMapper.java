package org.ebudoskyi.houserent.mapper;

import org.ebudoskyi.houserent.dto.UserRegisterDTO;
import org.ebudoskyi.houserent.dto.UserResponseDTO;
import org.ebudoskyi.houserent.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponseDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserResponseDTO userDTO = new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
        if (user.getOwnedProperties() != null) {
            List<Long> propertyIds = user.getOwnedProperties().stream()
                    .map(Property::getId)
                    .toList();
            userDTO.setOwnedProperties(propertyIds);
        }
        if (user.getBookings() != null) {
            List<Long> bookingIds = user.getBookings().stream()
                    .map(Booking::getId)
                    .toList();
            userDTO.setBookings(bookingIds);
        }
        if (user.getReviews() != null) {
            List<Long> reviewIds = user.getReviews().stream()
                    .map(Review::getId)
                    .toList();
            userDTO.setReviews(reviewIds);
        }
        if (user.getSentMessages() != null) {
            List<Long> messageIds = user.getSentMessages().stream()
                    .map(Message::getId)
                    .toList();
            userDTO.setBookings(messageIds);
        }
        if (user.getReceivedMessages() != null) {
            List<Long> messageIds = user.getReceivedMessages().stream()
                    .map(Message::getId)
                    .toList();
            userDTO.setReceivedMessages(messageIds);
        }
        return userDTO;
    }

    public User toEntity(UserResponseDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
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



    public List<UserResponseDTO> toDTOList(List<User> users) {
        if (users == null) {
            return null;
        }

        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<User>  toEntityList(List<UserResponseDTO> userDTOs) {
        if (userDTOs == null) {
            return null;
        }
        return userDTOs.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
