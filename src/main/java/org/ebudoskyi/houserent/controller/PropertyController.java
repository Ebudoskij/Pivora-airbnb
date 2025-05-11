package com.example.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PropertyController {

    @GetMapping("/properties")
    public String showProperties() {
        // Повертаємо сторінку зі списком нерухомості
        return "search"; // Використовуємо шаблон search.html
    }

    @GetMapping("/property/{id}")
    public String showPropertyDetails() {
        // Повертаємо сторінку з детальною інформацією про нерухомість
        return "property_details"; // Використовуємо шаблон property_details.html
    }
}
