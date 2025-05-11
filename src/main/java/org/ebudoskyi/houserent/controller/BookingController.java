package org.ebudoskyi.houserent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingController {

    @GetMapping("/book")
    public String bookProperty() {
        // Повертаємо сторінку, де можна забронювати нерухомість
        return "property_details"; // Використовуємо шаблон property_details.html
    }
}
