package org.ebudoskyi.houserent.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.ebudoskyi.houserent.dto.UserLoginDTO;
import org.ebudoskyi.houserent.dto.UserRegisterDTO;
import org.ebudoskyi.houserent.dto.UserResponseDTO;
import org.ebudoskyi.houserent.mapper.UserMapper;
import org.ebudoskyi.houserent.model.User;
import org.ebudoskyi.houserent.service.UserService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService,  AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model, HttpSession session) {
        model.addAttribute("userDTO", new UserRegisterDTO());
        return "users/register"; // templates/users/register.html
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestBody @Valid UserRegisterDTO userDTO,
            HttpSession session,
            Model model) {
        try {
            User user = userService.registerUser(userDTO);
            session.setAttribute("userId", user.getId()); // optional
            return "redirect:/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "users/register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model , HttpSession session) {
        model.addAttribute("userDTO", new UserLoginDTO());
        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(
            @ModelAttribute("userDTO") @Valid UserLoginDTO userDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDTO", userDTO);
            return "users/login";
        }

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/";

        } catch (AuthenticationException ex) {
            redirectAttributes.addFlashAttribute("loginErrorMsg", "Неправильна пошта або пароль");
            redirectAttributes.addFlashAttribute("userDTO", userDTO);
            return "redirect:/users/login";
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
        return "redirect:/users/register"; // Redirect to the login page
    }

}

