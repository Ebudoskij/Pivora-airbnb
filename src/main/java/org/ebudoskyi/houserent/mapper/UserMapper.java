package org.ebudoskyi.houserent.mapper;

import org.ebudoskyi.houserent.dto.UserDTO;
import org.ebudoskyi.houserent.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
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

    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        return user;
    }

    public List<UserDTO> toDTOList(List<User> users) {
        if (users == null) {
            return null;
        }

        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<User>  toEntityList(List<UserDTO> userDTOs) {
        if (userDTOs == null) {
            return null;
        }
        return userDTOs.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
