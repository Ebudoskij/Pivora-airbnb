package com.example.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/register")
    public String showRegistrationForm() {
        // Повертаємо сторінку для реєстрації
        return "index"; // Використовуємо шаблон index.html
    }

    @GetMapping("/login")
    public String showLoginForm() {
        // Повертаємо сторінку для входу
        return "index"; // Використовуємо шаблон index.html
    }

    @GetMapping("/profile")
    public String showUserProfile() {
        // Повертаємо сторінку профілю користувача
        return "index"; // Використовуємо шаблон index.html
    }
}
