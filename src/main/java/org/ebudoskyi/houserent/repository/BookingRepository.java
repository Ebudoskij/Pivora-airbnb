package org.ebudoskyi.houserent.repository;

import org.ebudoskyi.houserent.model.Booking;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.property.id = :propertyId " +
            "AND b.endDate > :startDate AND b.startDate < :endDate")
    List<Booking> findOverlappingBookings(@Param("propertyId") Long propertyId,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);

    boolean existsByPropertyIdAndStartDateBetweenOrEndDateBetween(Long propertyId, LocalDate startDate, LocalDate endDate, LocalDate startDate1, LocalDate endDate1);

    List<Booking> findByTenant(User user);

    List<Booking> findByProperty(Property property);
}

