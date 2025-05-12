package org.ebudoskyi.houserent.controller;

import jakarta.servlet.http.HttpSession;
import org.ebudoskyi.houserent.dto.ReviewDTO;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.model.Review;
import org.ebudoskyi.houserent.service.PropertyService;
import org.ebudoskyi.houserent.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final PropertyService propertyService;

    @Autowired
    public ReviewController(ReviewService reviewService, PropertyService propertyService) {
        this.reviewService = reviewService;
        this.propertyService = propertyService;
    }

    // ------------------------
    // 1. Форма створення відгуку
    // ------------------------
    @GetMapping("/new")
    public String showReviewForm(@RequestParam("propertyId") Long propertyId,
                                 Model model,
                                 HttpSession session) {

        if (session.getAttribute("authenticated") == null) {
            return "redirect:/login";
        }

        Property property = propertyService.getPropertyById(propertyId);
        model.addAttribute("property", property);
        model.addAttribute("reviewDTO", new ReviewDTO());
        return "reviews/form";
    }

    // ------------------------
    // 2. Обробка створення відгуку
    // ------------------------
    @PostMapping
    public String submitReview(@ModelAttribute("reviewDTO") ReviewDTO reviewDTO,
                               HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        reviewDTO.setAuthorId(userId);
        reviewDTO.setDate(LocalDate.now());

        reviewService.saveReview(reviewDTO);
        return "redirect:/property?id=" + reviewDTO.getPropertyId();
    }

    // ------------------------
    // 3. Перегляд усіх відгуків до помешкання
    // ------------------------
    @GetMapping
    public String viewPropertyReviews(@RequestParam("propertyId") Long propertyId, Model model) {
        List<Review> reviews = reviewService.getReviewsByPropertyId(propertyId);
        model.addAttribute("reviews", reviews);
        return "reviews/list";
    }

    // ------------------------
    // 4. Видалення відгуку (доступне лише адміну)
    // ------------------------
    @PostMapping("/delete")
    public String deleteReview(@RequestParam("reviewId") Long reviewId, HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin != null && isAdmin) {
            reviewService.deleteById(reviewId);
        }
        return "redirect:/dashboard";
    }
}
