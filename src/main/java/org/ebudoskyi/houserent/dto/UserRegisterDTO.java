package org.ebudoskyi.houserent.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "email is mandatory")
    @Email
    private String email;

    @NotBlank (message = "password is mandatory")
    @Size(min = 8, message= "Password must be at least 8 symbols long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$",
            message = "Password must contain at least one uppercase letter, one digit and special symbol from list: !@#$%^&*")
    private String password;
}
