package org.ebudoskyi.houserent.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.ebudoskyi.houserent.dto.PropertyCreationDTO;
import org.ebudoskyi.houserent.dto.PropertyResponseDTO;
import org.ebudoskyi.houserent.dto.PropertySearchDTO;
import org.ebudoskyi.houserent.mapper.PropertyMapper;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService propertyService;
    private final PropertyMapper propertyMapper;

    @Autowired
    public PropertyController(PropertyService propertyService,  PropertyMapper propertyMapper) {
        this.propertyService = propertyService;
        this.propertyMapper = propertyMapper;
    }

    @GetMapping("/createProperty")
    public String showPropertyForm(Model model,  HttpSession session) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/users/login";
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
            return "redirect:/users/login";
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

    @PostMapping("/delete")
    public String deleteProperty(@RequestParam Long propertyId, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        Boolean authenticated = (Boolean) session.getAttribute("authenticated");

        if (userId == null || authenticated == null) {
            return "redirect:/users/login";  // Ensure the user is logged in
        }

        try {
            // Call service to delete the property
            propertyService.deleteProperty(userId, propertyId);
            return "redirect:/properties/list";  // Redirect to dashboard after deletion
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "/dashboard";  // Optionally show an error on the dashboard
        }
    }

    // Обробка результатів пошуку
    @PostMapping("/search")
    public String searchProperties(
            @Valid @ModelAttribute("searchDTO") PropertySearchDTO searchDTO,
            BindingResult bindingResult,
            Model model,
            HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userId");
        Boolean authenticated = (Boolean) session.getAttribute("authenticated");
        if (userId == null || !authenticated) {
            return "redirect:/users/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "dashboard";
        }

        if (searchDTO.getStartDate().isAfter(searchDTO.getEndDate())) {
            return "dashboard";
        }

        List<Property> availableProperties = propertyService.getAvailableProperties(searchDTO);
        model.addAttribute("properties", availableProperties);
        return "properties/search"; // тут буде сторінка зі списком житла
    }

    @GetMapping("/list")
    public String listProperties(Model model, HttpSession session) {
        Boolean authenticated = (Boolean) session.getAttribute("authenticated");
        Long userId = (Long) session.getAttribute("userId");
        if (authenticated == null || userId == null) {
            return "redirect:/users/login";
        }
        List<Property> properties = propertyService.getPropertiesByUserId(userId);
        model.addAttribute("properties", properties);
        return "properties/list";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam Long propertyId, Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Boolean authenticated = (Boolean) session.getAttribute("authenticated");

        if (userId == null || authenticated == null) {
            return "redirect:/users/login";
        }

        Property property = propertyService.getPropertyById(propertyId);
        PropertyResponseDTO propertyEditingDTO = propertyMapper.toDTO(property);
        model.addAttribute("propertyEditingDTO", propertyEditingDTO);
        return "properties/edit";
    }

    @PostMapping("/edit")
    public String editProperty(
            @ModelAttribute PropertyResponseDTO propertyEditingDTO,
            Model model,
            HttpSession session){
        System.out.println(propertyEditingDTO.getId());
        Long userId = (Long) session.getAttribute("userId");
        Boolean authenticated = (Boolean) session.getAttribute("authenticated");
        if (userId == null || authenticated == null) {
            return "redirect:/users/login";
        }
        propertyService.updateProperty(userId, propertyEditingDTO);
        return "redirect:/properties/list";
    }

}
