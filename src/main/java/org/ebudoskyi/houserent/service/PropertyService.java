package org.ebudoskyi.houserent.service;

import org.ebudoskyi.houserent.Exceptions.CustomExceptions.*;
import org.ebudoskyi.houserent.dto.PropertyCreationDTO;
import org.ebudoskyi.houserent.dto.PropertyResponseDTO;
import org.ebudoskyi.houserent.dto.PropertySearchDTO;
import org.ebudoskyi.houserent.mapper.PropertyMapper;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.model.User;
import org.ebudoskyi.houserent.repository.PropertyRepository;
import org.ebudoskyi.houserent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;
    private final UserRepository userRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository, PropertyMapper propertyMapper, UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
        this.userRepository = userRepository;
    }

    public Property createProperty(Long userId, PropertyCreationDTO propertyDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
        Property property = propertyMapper.ToEntity(propertyDTO);
        property.setOwner(user);
        return propertyRepository.save(property);
    }

    public List<Property> getPropertiesByUserId(Long userId) {
        return propertyRepository.findByOwnerId(userId);
    }

    public List<Property> getAvailableProperties(PropertySearchDTO propertySearch) {
        String city = propertySearch.getCity();
        LocalDate startDate = propertySearch.getStartDate();
        LocalDate endDate = propertySearch.getEndDate();

        if (endDate.isBefore(startDate) || startDate.isEqual(endDate)) {
            throw new SearchDateException("Start date must be after end date");
        }

        return propertyRepository.findAvailablePropertiesByCityAndDates(city, startDate, endDate);
    }

    public Property getPropertyById(Long propertyId){
        return propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("Property witn id" + propertyId + " not found"));
    }

    public Property updateProperty(Long userId, PropertyResponseDTO propertyDetails) {
        Property property = propertyRepository.findById(propertyDetails.getId())
                .orElseThrow(() -> new PropertyNotFoundException("Property not found"));
        if (!property.getOwner().getId().equals(userId)) {
            throw new IllegalPropertyAccessException("User does not own this property");
        }

        property.setTitle(propertyDetails.getTitle());
        property.setDescription(propertyDetails.getDescription());
        property.setLocation(propertyDetails.getLocation());
        property.setPricePerNight(propertyDetails.getPricePerNight());
        property.setRooms(propertyDetails.getRooms());

        return propertyRepository.save(property);
    }

    public void deleteProperty(Long userId, Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("Property with id" + propertyId + " not found"));
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        if (!property.getOwner().getId().equals(owner.getId())) {
            throw new IllegalPropertyAccessException("You can only delete your property!");
        }
        propertyRepository.delete(property);
    }

}
