package org.ebudoskyi.houserent.repository;

import org.ebudoskyi.houserent.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    List<Availability> findByPropertyId(Long propertyId);

    List<Availability> findByPropertyIdAndAvailableDateGreaterThanEqual(Long propertyId, LocalDate date);

    boolean existsByPropertyIdAndAvailableDate(Long propertyId, LocalDate date);

    List<Availability> findByPropertyIdAndAvailableDateBetween(Long propertyId, LocalDate startDate, LocalDate endDate);
}
