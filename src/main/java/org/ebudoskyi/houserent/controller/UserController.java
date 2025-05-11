package org.ebudoskyi.houserent.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.ebudoskyi.houserent.dto.UserRegisterDTO;
import org.ebudoskyi.houserent.dto.UserResponseDTO;
import org.ebudoskyi.houserent.mapper.UserMapper;
import org.ebudoskyi.houserent.model.User;
import org.ebudoskyi.houserent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Registration form display
    @GetMapping("/register")
    public String showRegistrationForm(Model model, HttpSession session) {
        if (session.getAttribute("authenticated") != null) {
            return "redirect:/dashboard"; // already logged in
        }

        model.addAttribute("userDTO", new UserRegisterDTO());
        return "users/register"; // templates/users/register.html
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("userDTO") UserRegisterDTO userDTO,
            Model model,
            HttpSession session
    ) {
        try {
            User user = userService.registerUser(userDTO);
            session.setAttribute("authenticated", true);
            session.setAttribute("userEmail", user.getId()); // optional
            return "redirect:/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "users/register";
        }
    }

    // Login form display
    @GetMapping("/login")
    public String showLoginForm(Model model , HttpSession session) {
        if (session.getAttribute("authenticated") != null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("userDTO", new UserRegisterDTO());
        return "users/login";
    }
    // Login submission (simplified)
    @PostMapping("/login")
    public String loginUser(
            @ModelAttribute("userDTO") UserRegisterDTO userDTO,
            Model model,
            HttpSession session
    ) {
        try {
            User user = userService.login(userDTO);
            session.setAttribute("authenticated", true);
            session.setAttribute("userId", user.getId());
            return "redirect:/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "users/login";
        }
    }

    // User list (optional)
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/list"; // user/list.html
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the session to log the user out
        return "redirect:/login"; // Redirect to the login page
    }

}

