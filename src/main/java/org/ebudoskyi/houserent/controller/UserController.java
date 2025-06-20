package org.ebudoskyi.houserent.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.ebudoskyi.houserent.dto.UserLoginDTO;
import org.ebudoskyi.houserent.dto.UserRegisterDTO;
import org.ebudoskyi.houserent.service.JWTCookiesService;
import org.ebudoskyi.houserent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JWTCookiesService jwtCookiesService;

    @Autowired
    public UserController(UserService userService, JWTCookiesService jwtCookiesService) {
        this.userService = userService;
        this.jwtCookiesService = jwtCookiesService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model, HttpSession session) {
        model.addAttribute("userDTO", new UserRegisterDTO());
        return "users/register";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("userDTO") @Valid UserRegisterDTO userRegisterDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model,
            HttpServletResponse response
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDTO", userRegisterDTO);
            return "users/register";
        }

        Optional<UserLoginDTO> userLogin = userService.registerNewUser(userRegisterDTO);

        if (userLogin.isEmpty()) {
            model.addAttribute("emailErrorMsg", "This email is  already in use");
            return "users/register";
        }

        Optional<String> token = userService.login(userLogin.get());

        if (token.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "loginErrorMsg", "Auto login has failed, please try again");
            return "redirect:/users/login";
        }

        response.addCookie(jwtCookiesService.createCookie(token.get()));
        return "redirect:/";
    }


    @GetMapping("/login")
    public String showLoginForm(Model model) {
       model.addAttribute("userDTO", new UserLoginDTO());
       return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(
            @ModelAttribute("userDTO") @Valid UserLoginDTO userLoginDTO,
            BindingResult bindingResult,
            Model model,
            HttpServletResponse response
    ) {
        if (bindingResult.hasErrors()) {
            return "users/login";
        }

        Optional<String> token = userService.login(userLoginDTO);

        if (token.isEmpty()) {
            model.addAttribute("loginErrorMsg", "Password or email are invalid");
            return "users/login";
        }

        response.addCookie(jwtCookiesService.createCookie(token.get()));
        return "redirect:/";
    }
    @GetMapping("/force-logout")
    public String forceLogout(HttpServletRequest request,
                              HttpServletResponse response,
                              RedirectAttributes redirectAttributes)
    {
        new SecurityContextLogoutHandler().logout(request, response, null);
        redirectAttributes.addFlashAttribute("signatureError", "Please login again");
        return "redirect:/users/login";
    }
}

