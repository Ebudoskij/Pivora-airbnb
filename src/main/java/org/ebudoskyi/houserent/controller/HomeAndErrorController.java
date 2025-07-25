package org.ebudoskyi.houserent.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ebudoskyi.houserent.dto.PropertySearchDTO;
import org.ebudoskyi.houserent.model.CurrencyRates;
import org.ebudoskyi.houserent.model.UserPrincipal;
import org.ebudoskyi.houserent.service.CurrencyRatesApiServices.CurrencyRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeAndErrorController {

    private final CurrencyRatesService currencyRatesService;

    @Autowired
    public HomeAndErrorController(CurrencyRatesService currencyRatesService) {
        this.currencyRatesService = currencyRatesService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        if (!model.containsAttribute("searchDTO")) {
            model.addAttribute("searchDTO", new PropertySearchDTO());
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {

            Object principal = authentication.getPrincipal();

            if (principal instanceof UserPrincipal userPrincipal) {
                if (!userPrincipal.getUsername().isBlank()) {
                    model.addAttribute("username", "Welcome, " + userPrincipal.getName() + "!");
                }
            }
        }
        CurrencyRates currencyRates = currencyRatesService.getCurrencyRates("UAH");
        model.addAttribute("currencyRates", currencyRates);
        return "dashboard";
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if (exception != null) {
            model.addAttribute("error", exception.getMessage());
        } else if (status != null && status == 404) {
            model.addAttribute("error", "Page not found");
        } else {
            model.addAttribute("error", "Unexpected error occurred");
        }

        return "error";
    }
}
