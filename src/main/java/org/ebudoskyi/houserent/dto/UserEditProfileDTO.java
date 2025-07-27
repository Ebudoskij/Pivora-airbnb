package org.ebudoskyi.houserent.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEditProfileDTO {
    @Size(min = 1, message = "Name must consist of at least one character!")
    private String name;
    @Email
    private String email;
    @Pattern(regexp = "^$|^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$",
            message = "Password must be at least 8 characters long, including one uppercase letter, one digit, and one special character (!@#$%^&*)")
    private String password;
    private MultipartFile profileImage = null;
    @Pattern(regexp = "^$|^\\+\\d{1,4}\\s*\\d{1,5}\\s*\\d{4,10}$", message = "Wrong input format")
    private String phoneNumber;
    public UserEditProfileDTO(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
