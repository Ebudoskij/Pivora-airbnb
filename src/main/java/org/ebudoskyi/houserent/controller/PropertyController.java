package org.ebudoskyi.houserent.controller;

import jakarta.servlet.http.HttpSession;
import org.ebudoskyi.houserent.dto.PropertyCreationDTO;
import org.ebudoskyi.houserent.dto.PropertyResponseDTO;
import org.ebudoskyi.houserent.dto.PropertySearchDTO;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/createProperty")
    public String showPropertyForm(Model model,  HttpSession session) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/login";
        }

        model.addAttribute("propertyDTO", new PropertyCreationDTO());
        return "properties/create"; // Render property creation page
    }

    @PostMapping("/createProperty")
    public String createProperty(
            @ModelAttribute PropertyCreationDTO propertyDTO,
            Model model,
            HttpSession session
    ) {
        // Get the logged-in user's ID from the session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        // Property creation logic
        try {
            propertyService.createProperty(userId, propertyDTO);
            return "redirect:/dashboard"; // Redirect to the list of properties after creation
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "properties/create";
        }
    }

    @GetMapping("/deleteProperty/{propertyId}")
    public String deleteProperty(@PathVariable Long propertyId, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";  // Ensure the user is logged in
        }

        try {
            // Call service to delete the property
            propertyService.deleteProperty(userId, propertyId);
            return "redirect:/dashboard";  // Redirect to dashboard after deletion
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "properties/dashboard";  // Optionally show an error on the dashboard
        }
    }

    // Головна сторінка з формою пошуку
    @GetMapping("/dashboard")
    public String showSearchForm(Model model, HttpSession session) {
        Boolean authenticated = (Boolean) session.getAttribute("authenticated");
        if (authenticated == null || !authenticated) {
            return "redirect:/login";
        }

        model.addAttribute("searchDTO", new PropertySearchDTO());
        return "dashboard";
    }

    // Обробка результатів пошуку
    @PostMapping("/search")
    public String searchProperties(
            @ModelAttribute("searchDTO") PropertySearchDTO searchDTO,
            Model model,
            HttpSession session
    ) {
        Boolean authenticated = (Boolean) session.getAttribute("authenticated");
        if (authenticated == null || !authenticated) {
            return "redirect:/login";
        }

        List<Property> availableProperties = propertyService.getAvailableProperties(searchDTO);
        model.addAttribute("properties", availableProperties);
        return "search.html/result"; // тут буде сторінка зі списком житла
    }

}
