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

        return new PropertyResponseDTO(
                property.getId(),
                property.getTitle(),
                property.getDescription(),
                property.getCity(),
                property.getLocation(),
                property.getPricePerNight(),
                property.getRooms());
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
}
