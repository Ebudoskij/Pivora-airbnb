package org.ebudoskyi.houserent.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.ebudoskyi.houserent.Exceptions.CustomExceptions.BookingDateException;
import org.ebudoskyi.houserent.Exceptions.CustomExceptions.PropertyNotFoundException;
import org.ebudoskyi.houserent.dto.BookingRequestDTO;
import org.ebudoskyi.houserent.model.Booking;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.model.UserPrincipal;
import org.ebudoskyi.houserent.repository.PropertyRepository;
import org.ebudoskyi.houserent.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final PropertyRepository propertyRepository;

    @Autowired
    public BookingController(BookingService bookingService, PropertyRepository propertyRepository) {
        this.bookingService = bookingService;
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/create/{propertyId}")
    public String showBookingForm(@PathVariable Long propertyId, Model model) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("Property with id " + propertyId + " not found"));

        BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
        bookingRequestDTO.setPropertyId(propertyId);
        bookingRequestDTO.setPropertyTitle(property.getTitle());
        model.addAttribute("bookingDTO", bookingRequestDTO);

        return "bookings/form";
    }

    @PostMapping("/create")
    public String createBooking(
            @ModelAttribute("bookingDTO") @Valid BookingRequestDTO bookingRequestDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "bookings/form";
        }
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            Long userId = userPrincipal.getId();
            bookingService.createBooking(userId, bookingRequestDTO);
            return "redirect:/bookings/list";
    }

    @GetMapping("/list")
    public String showBookings(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Long userId = userPrincipal.getId();

        List<Booking> bookings = bookingService.getBookingsByUser(userId);
        model.addAttribute("bookings", bookings);
        return "bookings/list";
    }

    @PostMapping("/delete/{bookingId}")
    public String deleteBooking(@PathVariable Long bookingId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Long userId = userPrincipal.getId();

        bookingService.deleteBooking(userId, bookingId);
        return "redirect:/bookings/list";
    }
}
