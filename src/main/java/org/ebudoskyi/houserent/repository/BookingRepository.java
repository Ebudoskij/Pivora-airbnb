package org.ebudoskyi.houserent.repository;

import org.ebudoskyi.houserent.model.Booking;
import org.ebudoskyi.houserent.model.BookingEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByPropertyId(Long propertyId);

    List<Booking> findByTenantId(Long tenantId);

    List<Booking> findByStatus(BookingEnum status);

    List<Booking> findByPropertyIdAndEndDateGreaterThanEqualAndStartDateLessThanEqual(
            Long propertyId, LocalDate endDate, LocalDate startDate);

    List<Booking> findByPropertyIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long propertyId, LocalDate date);

    boolean existsByPropertyIdAndStartDateBetweenOrEndDateBetween(Long propertyId, LocalDate startDate, LocalDate endDate, LocalDate startDate1, LocalDate endDate1);
}

