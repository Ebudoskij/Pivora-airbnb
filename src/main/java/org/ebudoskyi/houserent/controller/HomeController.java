package org.ebudoskyi.houserent.controller;

import jakarta.servlet.http.HttpSession;
import org.ebudoskyi.houserent.dto.PropertySearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String dashboard(Model model) {
        if (!model.containsAttribute("searchDTO")) {
            model.addAttribute("searchDTO", new PropertySearchDTO());
        }
        return "dashboard";
    }
}
