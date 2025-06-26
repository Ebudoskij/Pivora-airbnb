package org.ebudoskyi.houserent.Exceptions;

import io.jsonwebtoken.MalformedJwtException;
import org.ebudoskyi.houserent.Exceptions.CustomExceptions.*;
import org.ebudoskyi.houserent.dto.BookingRequestDTO;
import org.ebudoskyi.houserent.dto.PropertySearchDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SignatureException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UserNotFoundException.class, PropertyNotFoundException.class, BookingNotFoundException.class, AlreadyBookedException.class})
    public String handleNotFoundExceptions(UserNotFoundException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/";
    }
    @ExceptionHandler(IllegalPropertyAccessException.class)
    public String handleIllegalPropertyAccessException(IllegalPropertyAccessException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "/properties/list";
    }
    @ExceptionHandler(SearchDateException.class)
    public String handleSearchDateException(SearchDateException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("searchErrorMsg", e.getMessage());
        return "redirect:/";
    }
    @ExceptionHandler(IllegalBookingAccessException.class)
    public String handleIllegalBookingAccessException(IllegalBookingAccessException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "/bookings/list";
    }
    @ExceptionHandler(BookingDateException.class)
    public String handleBookingDateException(BookingDateException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("bookingErrorMsg", e.getMessage());
        Long propertyId = e.getPropertyId();
        return "redirect:/bookings/create/" + propertyId;
    }
    @ExceptionHandler(SignatureException.class)
    public String handleSignatureException() {
        return "redirect:/users/force-logout";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Oops, an unexpected error occurred. Please try again later.");
        return "redirect:/";
    }
}
