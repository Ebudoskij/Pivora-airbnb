package org.ebudoskyi.houserent.service;

import org.ebudoskyi.houserent.dto.BookingDTO;
import org.ebudoskyi.houserent.dto.BookingRequestDTO;
import org.ebudoskyi.houserent.model.Booking;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.model.User;
import org.ebudoskyi.houserent.repository.BookingRepository;
import org.ebudoskyi.houserent.repository.PropertyRepository;
import org.ebudoskyi.houserent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
    public Booking createBooking(Long userId, BookingRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                dto.getPropertyId(), dto.getStartDate(), dto.getEndDate()
        );

        if (!overlappingBookings.isEmpty()) {
            throw new IllegalArgumentException("This property is already booked for the selected dates");
        }

        long nights = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate());
        double totalPrice = nights * property.getPricePerNight();

        Booking booking = new Booking();
        booking.setTenant(user);
        booking.setProperty(property);
        booking.setStartDate(dto.getStartDate());
        booking.setEndDate(dto.getEndDate());
        booking.setTotalPrice(totalPrice);

        return bookingRepository.save(booking);
    }


    // Cancel a booking
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        bookingRepository.delete(booking);
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

    public void deleteBooking(Long userId, Long bookingId) {
        Booking booking =  bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        User  user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!booking.getTenant().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You can only delete your booking!");
        }
        bookingRepository.delete(booking);
    }
}
