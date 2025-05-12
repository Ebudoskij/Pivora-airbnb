package org.ebudoskyi.houserent.mapper;

import org.ebudoskyi.houserent.dto.PropertyCreationDTO;
import org.ebudoskyi.houserent.dto.PropertyResponseDTO;
import org.ebudoskyi.houserent.model.Booking;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.model.Review;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PropertyMapper {

    public PropertyResponseDTO toDTO(Property property) {
        if (property == null) {
            return null;
        }
        PropertyResponseDTO propertyDTO = new PropertyResponseDTO(
                property.getId(),
                property.getTitle(),
                property.getDescription(),
                property.getCity(),
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
        return propertyDTO;
    }

    public Property toEntity(PropertyResponseDTO propertyDTO) {
        if (propertyDTO == null) {
            return null;
        }
        Property property = new Property();
        property.setId(propertyDTO.getId());
        property.setTitle(propertyDTO.getTitle());
        property.setDescription(propertyDTO.getDescription());
        property.setCity(propertyDTO.getCity());
        property.setLocation(propertyDTO.getLocation());
        property.setPricePerNight(propertyDTO.getPricePerNight());
        property.setRooms(propertyDTO.getRooms());
        return property;
    }

    public Property ToEntity(PropertyCreationDTO  propertyCreationDTO) {
        if (propertyCreationDTO == null) {
            return null;
        }
        Property property = new Property();
        property.setTitle(propertyCreationDTO.getTitle());
        property.setDescription(propertyCreationDTO.getDescription());
        property.setCity(propertyCreationDTO.getCity());
        property.setLocation(propertyCreationDTO.getLocation());
        property.setRooms(propertyCreationDTO.getRooms());
        property.setPricePerNight(propertyCreationDTO.getPricePerNight());
        return property;
    }

    public List<PropertyResponseDTO> toDTOList(List<Property> properties) {
        if (properties == null) {
            return null;
        }

        return properties.stream().map(this::toDTO).collect(Collectors.toList());
    }
    public List<Property>  toEntityList(List<PropertyResponseDTO> properties) {
        if (properties == null) {
            return null;
        }
        return properties.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
