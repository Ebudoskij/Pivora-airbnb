package org.ebudoskyi.houserent.controller;

import jakarta.servlet.http.HttpSession;
import org.ebudoskyi.houserent.dto.PropertySearchDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToRegistration(Model model,  HttpSession session) {
        return "redirect:/users/login"; // Redirects root to /dashboard
    }

    @GetMapping("/dashboard")
    public String showSearchForm(Model model, HttpSession session) {
        Boolean authenticated = (Boolean) session.getAttribute("authenticated");
        if (authenticated == null || !authenticated) {
            return "redirect:/users/login";
        }

        model.addAttribute("searchDTO", new PropertySearchDTO());
        return "dashboard";
    }
}
