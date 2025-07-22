package org.ebudoskyi.houserent.repository;

import org.ebudoskyi.houserent.model.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyImagesRepository extends JpaRepository<PropertyImage, Long> {
    public void deleteAllByPropertyId(Long propertyId);

    List<PropertyImage> getAllByPropertyId(Long propertyId);
}
