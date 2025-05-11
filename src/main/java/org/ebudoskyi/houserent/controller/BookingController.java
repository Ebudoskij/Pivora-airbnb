package org.ebudoskyi.houserent.controller;

import jakarta.servlet.http.HttpSession;
import org.ebudoskyi.houserent.dto.BookingDTO;
import org.ebudoskyi.houserent.dto.BookingRequestDTO;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.repository.PropertyRepository;
import org.ebudoskyi.houserent.service.BookingService;
import org.ebudoskyi.houserent.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingController {

    private final BookingService bookingService;
    private final PropertyRepository propertyRepository;

    @Autowired
    public BookingController(BookingService bookingService, PropertyRepository propertyRepository) {
        this.bookingService = bookingService;
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/new")
    public String showBookingForm(@RequestParam("propertyId") Long propertyId, Model model, HttpSession session) {
        // Перевірка авторизації
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/login";
        }

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Житло не знайдено"));

        model.addAttribute("property", property);
        model.addAttribute("bookingDTO", new BookingDTO()); // DTO з датами та ціною

        return "bookings/form";
    }

    @PostMapping("/bookings")
    public String createBooking(
            @ModelAttribute("bookingDTO") BookingRequestDTO bookingRequestDTO,
            HttpSession session,
            Model model
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            bookingService.createBooking(userId, bookingRequestDTO);
            return "redirect:/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "bookings/form";
        }
    }

}
