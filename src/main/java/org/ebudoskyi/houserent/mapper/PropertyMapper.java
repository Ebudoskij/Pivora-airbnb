package org.ebudoskyi.houserent.mapper;

import org.ebudoskyi.houserent.dto.PropertyDTO;
import org.ebudoskyi.houserent.model.Availability;
import org.ebudoskyi.houserent.model.Booking;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.model.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyMapper {

    public PropertyDTO toDTO(Property property) {
        if (property == null) {
            return null;
        }
        PropertyDTO propertyDTO = new PropertyDTO(
                property.getId(),
                property.getTitle(),
                property.getDescription(),
                property.getLocation(),
                property.getPricePerNight(),
                property.getRooms(),
                property.getOwner().getId());

        if (property.getReviews() != null) {
            List<Long> reviewsIds = property.getReviews().stream()
                    .map(Review::getId)
                    .toList();
            propertyDTO.setReviewsIds(reviewsIds);
        }
        if (property.getBookings() != null) {
            List<Long> bookingIds = property.getBookings().stream()
                    .map(Booking::getId)
                    .toList();
            propertyDTO.setBookingsIds(bookingIds);
        }
        if (property.getAvailabilities() != null) {
            List<Long> availabilityIds = property.getAvailabilities().stream()
                    .map(Availability::getId)
                    .toList();
            propertyDTO.setAvailabilitiesIds(availabilityIds);
        }
        return propertyDTO;
    }

    public Property toEntity(PropertyDTO propertyDTO) {
        if (propertyDTO == null) {
            return null;
        }
        Property property = new Property();
        property.setId(propertyDTO.getId());
        property.setTitle(propertyDTO.getTitle());
        property.setDescription(propertyDTO.getDescription());
        property.setLocation(propertyDTO.getLocation());
        property.setPricePerNight(propertyDTO.getPricePerNight());
        property.setRooms(propertyDTO.getRooms());
        return property;
    }

    public List<PropertyDTO> toDTOList(List<Property> properties) {
        if (properties == null) {
            return null;
        }

        return properties.stream().map(this::toDTO).collect(Collectors.toList());
    }
    public List<Property>  toEntityList(List<PropertyDTO> properties) {
        if (properties == null) {
            return null;
        }
        return properties.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
