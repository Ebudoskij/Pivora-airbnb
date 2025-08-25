package org.ebudoskyi.houserent.service;

import org.ebudoskyi.houserent.Exceptions.CustomExceptions.*;
import org.ebudoskyi.houserent.dto.PropertyResponseDTO;
import org.ebudoskyi.houserent.dto.PropertySearchDTO;
import org.ebudoskyi.houserent.mapper.PropertyMapper;
import org.ebudoskyi.houserent.model.CurrencyRates;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.model.User;
import org.ebudoskyi.houserent.repository.PropertyRepository;
import org.ebudoskyi.houserent.repository.UserRepository;
import org.ebudoskyi.houserent.service.CurrencyRatesApiServices.CurrencyRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;
    private final UserRepository userRepository;
    private final PropertyImagesService propertyImagesService;
    private final CurrencyRatesService currencyRatesService;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository,
                           PropertyMapper propertyMapper,
                           UserRepository userRepository,
                           PropertyImagesService propertyImagesService,
                           CurrencyRatesService currencyRatesService) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
        this.userRepository = userRepository;
        this.propertyImagesService = propertyImagesService;
        this.currencyRatesService = currencyRatesService;
    }

    public Property createProperty(Long userId,
                                   String title,
                                   String description,
                                   String city,
                                   String location,
                                   double price,
                                   String currency,
                                   int rooms) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
        double convertedPrice = convertPrice(price, currency);
        Property property = new Property();
        property.setTitle(title);
        property.setDescription(description);
        property.setCity(city);
        property.setLocation(location);
        property.setPricePerNight(Math.round(convertedPrice));
        property.setRooms(rooms);
        property.setOwner(user);
        return propertyRepository.save(property);
    }

    public List<PropertyResponseDTO> getPropertiesByUserId(Long userId, String currency) {
        return propertyRepository.findByOwnerId(userId).stream()
                .map(property -> mapToPropertyResponse(currency, property))
                .toList();
    }
    @Transactional
    public List<PropertyResponseDTO> getAvailableProperties(PropertySearchDTO propertySearch,
                                                            String currency) {
        String city = propertySearch.getCity();
        LocalDate startDate = propertySearch.getStartDate();
        LocalDate endDate = propertySearch.getEndDate();

        if (endDate.isBefore(startDate) || startDate.isEqual(endDate)) {
            throw new SearchDateException("Start date must be after end date");
        }

        return propertyRepository.findAvailablePropertiesByCityAndDates(city, startDate, endDate).stream()
            .map(property ->  {
                mapToPropertyResponse(currency, property);
                PropertyResponseDTO response = mapToPropertyResponse(currency, property);
                response.setPhoneNumber(property.getOwner().getPhoneNumber());
                return response;
            })
            .toList();
    }

    private PropertyResponseDTO mapToPropertyResponse(String currency, Property property) {
        PropertyResponseDTO response = propertyMapper.toDTO(property);
        double rawPrice = property.getPricePerNight();
        CurrencyRates currencyRates = currencyRatesService.getCurrencyRates("UAH");
        double convertedPrice = switch(currency){
            case "USD" -> rawPrice/currencyRates.getUSD();
            case "EUR" -> rawPrice/currencyRates.getEUR();
            case "GBP" -> rawPrice/currencyRates.getGBP();
            case "UAH" -> rawPrice;
            default -> throw new IllegalArgumentException("Invalid currency:" + currency);
        };
        response.setPricePerNight(Math.round(convertedPrice));
        response.setCurrency(currency);
        response.setImages(propertyImagesService.getImages(property.getId()));

        return  response;
    }

    public Property getPropertyById(Long propertyId){
        return propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("Property witn id" + propertyId + " not found"));
    }

    public Property updateProperty(Long userId,
                                   Long propertyId,
                                   String title,
                                   String description,
                                   String city,
                                   String location,
                                   double price,
                                   String currency,
                                   int rooms) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found"));
        if (!property.getOwner().getId().equals(userId)) {
            throw new IllegalPropertyAccessException("User does not own this property");
        }
        property.setTitle(title);
        property.setDescription(description);
        property.setCity(city);
        property.setLocation(location);
        property.setPricePerNight(convertPrice(price, currency));
        property.setRooms(rooms);

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

    public List<String> getAvailableCities() {
        return propertyRepository.getCities().orElse(null);
    }

    private double convertPrice(double price, String currency) {
        CurrencyRates currencyRates = currencyRatesService.getCurrencyRates("UAH");
        return switch (currency){
            case "EUR" -> currencyRates.getEUR()*price;
            case "USD" -> currencyRates.getUSD()*price;
            case "GBP" -> currencyRates.getGBP()*price;
            case "UAH" -> price;
            default -> throw new IllegalArgumentException("Invalid currency:" + currency);
        };
    }
}
