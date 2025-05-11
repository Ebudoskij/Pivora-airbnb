package org.ebudoskyi.houserent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReviewController {

    // Мапінг для сторінки відгуків
    @GetMapping("/reviews")
    public String showReviews() {
        // Повертаємо сторінку для перегляду відгуків
        return "reviews"; // це має бути файл reviews.html у папці templates
    }
}
