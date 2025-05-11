package org.ebudoskyi.houserent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AvailabilityController {

    @GetMapping("/availability")
    public String showAvailability() {
        // Повертаємо сторінку, яка показує доступність
        return "search"; // Використовуємо шаблон search.html
    }
}
