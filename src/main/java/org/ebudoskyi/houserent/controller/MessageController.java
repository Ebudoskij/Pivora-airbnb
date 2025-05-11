package org.ebudoskyi.houserent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {

    @GetMapping("/messages")
    public String showMessages() {
        // Повертаємо сторінку з повідомленнями
        return "search"; // Використовуємо шаблон search.html
    }
}
