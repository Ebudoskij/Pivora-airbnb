package org.ebudoskyi.houserent.service;

import org.ebudoskyi.houserent.dto.BookingDTO;
import org.ebudoskyi.houserent.model.Booking;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.model.User;
import org.ebudoskyi.houserent.repository.BookingRepository;
import org.ebudoskyi.houserent.repository.PropertyRepository;
import org.ebudoskyi.houserent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookingService(
            BookingRepository bookingRepository,
            PropertyRepository propertyRepository,
            UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    // Check if the property is available for the specified date range
    public boolean isPropertyAvailable(Long propertyId, LocalDate startDate, LocalDate endDate) {
        return !bookingRepository.existsByPropertyIdAndStartDateBetweenOrEndDateBetween(
                propertyId, startDate, endDate, startDate, endDate);
    }

    // Create a new booking
    public Booking createBooking(BookingDTO bookingDTO) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));

        User tenant = userRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Check if the property is available for the given date range
        if (!isPropertyAvailable(propertyId, startDate, endDate)) {
            throw new IllegalArgumentException("Property is not available for the selected dates");
        }

        // Create the booking
        Booking booking = new Booking();
        booking.setProperty(property);
        booking.setTenant(tenant);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingEnum.PENDING);  // Booking is initially pending

        return bookingRepository.save(booking);
    }

    // Cancel a booking
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        booking.setStatus(BookingEnum.CANCELLED);
        bookingRepository.save(booking);
    }

    // Get all bookings for a user
    public List<Booking> getBookingsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return bookingRepository.findByTenant(user);
    }

    // Get all bookings for a property
    public List<Booking> getBookingsByProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));

        return bookingRepository.findByProperty(property);
    }
}
