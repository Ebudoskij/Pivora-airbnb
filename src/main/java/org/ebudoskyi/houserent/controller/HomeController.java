package org.ebudoskyi.houserent.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToRegistration(Model model,  HttpSession session) {
        return "redirect:users/register"; // Redirects root to /dashboard
    }

    @GetMapping("/dashboard")
    public String renderDashboard(Model model, HttpSession session) {
        if (session.getAttribute("authenticated") != null) {
            return "dashboard";
        }
        return "redirest:/users/register";
    }

}
