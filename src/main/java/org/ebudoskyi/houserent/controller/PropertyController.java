package org.ebudoskyi.houserent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PropertyController {

    // Мапінг для сторінки властивостей
    @GetMapping("/properties")
    public String showProperties() {
        // Повертаємо сторінку для перегляду всіх об'єктів нерухомості
        return "properties"; // це має бути файл properties.html у папці templates
    }
}
