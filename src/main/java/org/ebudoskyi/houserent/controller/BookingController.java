package org.ebudoskyi.houserent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingController {

    // Мапінг для сторінки бронювання
    @GetMapping("/booking")
    public String showBookingPage() {
        // Повертаємо сторінку для бронювання
        return "booking"; // це має бути файл booking.html у папці templates
    }
}
