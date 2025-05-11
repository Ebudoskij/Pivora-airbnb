package org.ebudoskyi.houserent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AvailabilityController {

    // Мапінг для сторінки доступності
    @GetMapping("/availability")
    public String showAvailability() {
        // Повертаємо сторінку для перевірки доступності
        return "availability"; // це має бути файл availability.html у папці templates
    }
}
