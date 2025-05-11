package org.ebudoskyi.houserent.repository;

import jakarta.validation.constraints.Positive;
import org.ebudoskyi.houserent.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByOwnerId(Long userId);
    List<Property> findByLocation(String location);
    List<Property> findByLocationContainingIgnoreCase(String location);
    List<Property> findByPricePerNightBetween(double min, double max);
    List<Property> findByRoomsGreaterThanEqual(int roomsIsGreaterThan);

    // Пошук житла за містом та перевірка на доступність на вказаний період
    @Query("SELECT p FROM Property p WHERE p.city = :city AND p.id NOT IN " +
            "(SELECT b.property.id FROM Booking b WHERE (b.startDate BETWEEN :startDate AND :endDate) " +
            "OR (b.endDate BETWEEN :startDate AND :endDate))")
    List<Property> findAvailablePropertiesByCityAndDates(@Param("city") String city,
                                                         @Param("startDate") LocalDate startDate,
                                                         @Param("endDate") LocalDate endDate);
}
