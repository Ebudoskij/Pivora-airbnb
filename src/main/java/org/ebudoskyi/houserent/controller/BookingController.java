package org.ebudoskyi.houserent.controller;

import jakarta.servlet.http.HttpSession;
import org.ebudoskyi.houserent.dto.BookingDTO;
import org.ebudoskyi.houserent.dto.BookingRequestDTO;
import org.ebudoskyi.houserent.model.Booking;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.repository.PropertyRepository;
import org.ebudoskyi.houserent.service.BookingService;
import org.ebudoskyi.houserent.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/create")
    public String showBookingForm(@RequestParam("propertyId") Long propertyId, Model model, HttpSession session) {
        // Перевірка авторизації
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/users/login";
        }

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Житло не знайдено"));

        model.addAttribute("propertyName", property.getTitle());
        BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
        bookingRequestDTO.setPropertyId(propertyId);
        model.addAttribute("bookingDTO", bookingRequestDTO); // DTO з датами та ціною

        return "bookings/form";
    }

    @PostMapping("/create")
    public String createBooking(
            @ModelAttribute("bookingDTO") BookingRequestDTO bookingRequestDTO,
            HttpSession session,
            Model model
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }

        try {
            bookingService.createBooking(userId, bookingRequestDTO);
            return "redirect:/bookings/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "bookings/form";
        }
    }

    @GetMapping("/list")
    public String showBookings(Model model, HttpSession session) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/users/login";
        }
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        List<Booking> bookings = bookingService.getBookingsByUser(userId);
        model.addAttribute("bookings", bookings);
        return "bookings/list";
    }

    @PostMapping("/delete")
    public String deleteBooking(@RequestParam("bookingId") Long bookingId, Model model, HttpSession session){
        Long userId = (Long) session.getAttribute("userId");
        Boolean authenticated = (Boolean) session.getAttribute("authenticated");

        if (userId == null || authenticated == null) {
            return "redirect:/users/login";  // Ensure the user is logged in
        }

        try{
            bookingService.deleteBooking(userId, bookingId);
            return "redirect:/bookings/list";
        }
        catch (IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            return "/dashboard";
        }

    }
}
