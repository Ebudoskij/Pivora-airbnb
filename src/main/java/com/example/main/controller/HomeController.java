package com.example.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Маршрут для головної сторінки
    @GetMapping("/")
    public String home() {
        // Повертаємо назву шаблону index.html
        return "index"; // Це означає, що буде використано шаблон src/main/resources/templates/index.html
    }
}
