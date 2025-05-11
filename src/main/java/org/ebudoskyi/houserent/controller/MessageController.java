package org.ebudoskyi.houserent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {

    // Мапінг для сторінки повідомлень
    @GetMapping("/messages")
    public String showMessages() {
        // Повертаємо сторінку для перегляду повідомлень
        return "messages"; // це має бути файл messages.html у папці templates
    }
}
