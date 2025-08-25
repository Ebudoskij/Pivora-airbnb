package org.ebudoskyi.houserent.service;

import org.ebudoskyi.houserent.Exceptions.CustomExceptions.PropertyNotFoundException;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.model.PropertyImage;
import org.ebudoskyi.houserent.repository.PropertyImagesRepository;
import org.ebudoskyi.houserent.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyImagesService {
    private final PropertyImagesRepository propertyImagesRepository;
    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyImagesService(PropertyImagesRepository propertyImagesRepository, PropertyRepository propertyRepository) {
        this.propertyImagesRepository = propertyImagesRepository;
        this.propertyRepository = propertyRepository;
    }

    @Transactional
    public void uploadOrUpdateImages(Long propertyId, List<MultipartFile> newImages) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found with id: " + propertyId));

        propertyImagesRepository.deleteAllByPropertyId(propertyId);

        List<PropertyImage> toSave = newImages.stream()
                .map(file -> {
                    try {
                        PropertyImage image = new PropertyImage();
                        image.setImage(file.getBytes());
                        image.setProperty(property);
                        return image;
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to read image file", e);
                    }
                })
                .collect(Collectors.toList());

        propertyImagesRepository.saveAll(toSave);
    }

    @Transactional
    public List<String> getImages(Long propertyId) {
        return propertyImagesRepository.getAllByPropertyId(propertyId).stream()
                .filter(img -> img.getImage() != null && img.getImage().length > 0)
                .map(object -> "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(object.getImage()))
                .collect(Collectors.toList());
    }
}
