package org.ebudoskyi.houserent.repository;

import jakarta.validation.constraints.Positive;
import org.ebudoskyi.houserent.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByOwnerId(Long propertyId, LocalDate availableDate);
    List<Property> findByLocation(String location);
    List<Property> findByLocationContainingIgnoreCase(String location);
    List<Property> findByPricePerNightBetween(double min, double max);
    List<Property> findByRoomsGreaterThanEqual(int roomsIsGreaterThan);
}
