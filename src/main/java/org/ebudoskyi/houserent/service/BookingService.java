package org.ebudoskyi.houserent.service;

import org.ebudoskyi.houserent.Exceptions.CustomExceptions.*;
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

    public boolean isPropertyAvailable(Long propertyId, LocalDate startDate, LocalDate endDate) {
        return !bookingRepository.existsByPropertyIdAndStartDateBetweenOrEndDateBetween(
                propertyId, startDate, endDate, startDate, endDate);
    }

    public Booking createBooking(Long userId, BookingRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with " + userId + " not found"));
        Long propertyId = dto.getPropertyId();
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("Property with id " + propertyId + " not found"));

        if (dto.getStartDate().isBefore(LocalDate.now()) || dto.getStartDate().isEqual(LocalDate.now())){
            throw new BookingDateException("Check-in date must be in future");
        }

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new BookingDateException("Check-out date must be after check-in date");
        }

        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                dto.getPropertyId(), dto.getStartDate(), dto.getEndDate()
        );

        if (!overlappingBookings.isEmpty()) {
            throw new AlreadyBookedException("This property is already booked for the selected dates");
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

    public List<Booking> getBookingsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        return bookingRepository.findByTenant(user);
    }

    public void deleteBooking(Long userId, Long bookingId) {
        Booking booking =  bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking with id" + bookingId + " not found"));
        User  user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with Id" + userId + " qnot found"));
        if (!booking.getTenant().getId().equals(user.getId())) {
            throw new IllegalBookingAccessException("You can only delete your booking!");
        }
        bookingRepository.delete(booking);
    }
}
