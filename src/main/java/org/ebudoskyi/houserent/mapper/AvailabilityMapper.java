package org.ebudoskyi.houserent.mapper;

import org.ebudoskyi.houserent.dto.AvailabilityDTO;
import org.ebudoskyi.houserent.model.Availability;

import java.util.List;
import java.util.stream.Collectors;

public class AvailabilityMapper {

    public AvailabilityDTO toDTO(Availability availability) {
        if (availability == null) {
            return null;
        }
        AvailabilityDTO availabilityDTO = new AvailabilityDTO(
                availability.getId(),
                availability.getProperty().getId(),
                availability.getAvailableDate()
        );
        return availabilityDTO;
    }

    public Availability toEntity(AvailabilityDTO availabilityDTO) {
        if (availabilityDTO == null) {
            return null;
        }
        Availability availability = new Availability();
        availability.setId(availabilityDTO.getId());
        availability.setAvailableDate(availabilityDTO.getAvailableDate());
        return availability;
    }

    public List<AvailabilityDTO> toDTOList(List<Availability> availabilities) {
        if (availabilities == null) {
            return null;
        }
        return availabilities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<Availability> toEntityList(List<AvailabilityDTO> availabilities) {
        if (availabilities == null) {
            return null;
        }
        return availabilities.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
