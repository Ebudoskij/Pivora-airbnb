package org.ebudoskyi.houserent.controller;

import jakarta.validation.Valid;
import org.ebudoskyi.houserent.dto.PropertyCreationDTO;
import org.ebudoskyi.houserent.dto.PropertyResponseDTO;
import org.ebudoskyi.houserent.dto.PropertySearchDTO;
import org.ebudoskyi.houserent.mapper.PropertyMapper;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.model.UserPrincipal;
import org.ebudoskyi.houserent.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String showPropertyForm(Model model) {
        model.addAttribute("propertyCreationDTO", new PropertyCreationDTO());
        return "properties/create";
    }

    @PostMapping("/createProperty")
    public String createProperty(
            @ModelAttribute("propertyCreationDTO") @Valid PropertyCreationDTO propertyDTO,
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            BindingResult bindingResult
    ) {
        if  (bindingResult.hasErrors()) {
            return "properties/create";
        }
            Long userId = userPrincipal.getId();
            propertyService.createProperty(userId, propertyDTO);
            return "redirect:/properties/list";
    }

    @PostMapping("/delete/{propertyId}")
    public String deleteProperty(@PathVariable Long propertyId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
            Long userId = userPrincipal.getId();
            propertyService.deleteProperty(userId, propertyId);
            return "redirect:/properties/list";
    }

    @GetMapping("/list")
    public String listProperties(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        List<Property> properties = propertyService.getPropertiesByUserId(userId);
        model.addAttribute("properties", properties);
        return "properties/list";
    }

    @PostMapping("/search")
    public String searchProperties(
            @Valid @ModelAttribute("searchDTO") PropertySearchDTO searchDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "dashboard";
        }
            List<Property> availableProperties = propertyService.getAvailableProperties(searchDTO);
            redirectAttributes.addFlashAttribute("properties", availableProperties);
            return "redirect:/properties/search";
    }

    @GetMapping("/search")
    public String searchProperties(){
        return "properties/search";
    }


    @GetMapping("/edit/{propertyId}")
    public String showEditForm(@PathVariable Long propertyId, Model model) {
        Property property = propertyService.getPropertyById(propertyId);
        PropertyResponseDTO propertyEditingDTO = propertyMapper.toDTO(property);
        model.addAttribute("propertyEditingDTO", propertyEditingDTO);
        return "properties/edit";
    }

    @PostMapping("/edit")
    public String editProperty(
            @ModelAttribute("propertyEditingDTO") @Valid PropertyResponseDTO propertyEditingDTO,
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ){
        if  (bindingResult.hasErrors()) {
            return "properties/edit";
        }
        try {
            Long userId = userPrincipal.getId();
            propertyService.updateProperty(userId, propertyEditingDTO);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("editErrorMsg", e.getMessage());
        }
        return "redirect:/properties/list";
    }
}
