package org.ebudoskyi.houserent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    // Мапінг для сторінки реєстрації
    @GetMapping("/register")
    public String showRegistrationForm() {
        // Повертаємо сторінку для реєстрації
        return "register"; // це має бути файл register.html у папці templates
    }

    // Мапінг для сторінки входу
    @GetMapping("/login")
    public String showLoginForm() {
        // Повертаємо сторінку для входу
        return "login"; // це має бути файл login.html у папці templates
    }

    // Мапінг для профілю користувача
    @GetMapping("/profile")
    public String showUserProfile() {
        // Повертаємо сторінку профілю користувача
        return "profile"; // це має бути файл profile.html у папці templates
    }
}
