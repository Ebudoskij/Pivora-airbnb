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
    @Query("SELECT p FROM Property p WHERE p.owner.id = :ownerId AND p.createdDate = :date")
    List<Property> findByOwnerIdAndDate(@Param("ownerId") Long ownerId, @Param("date") LocalDate date);
    List<Property> findByLocation(String location);
    List<Property> findByLocationContainingIgnoreCase(String location);
    List<Property> findByPricePerNightBetween(double min, double max);
    List<Property> findByRoomsGreaterThanEqual(int roomsIsGreaterThan);
}
