package com.devkind.barebonesystem.dto.auth;

import com.devkind.barebonesystem.validation.Age;
import com.devkind.barebonesystem.validation.DateFormat;
import com.devkind.barebonesystem.validation.Regex;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterDto {
    @NotBlank
    @Regex(pattern = "^[a-zA-Z]+(?:[-\\s][a-zA-Z]+)*$", message = "Firstname only contains alphebetical characters and hyphens")
    private String firstname;
    @NotBlank
    @Regex(pattern = "^[a-zA-Z]+(?:[-\\s][a-zA-Z]+)*$", message = "Lastname only contains alphebetical characters and hyphens")
    private String lastname;

    @Age
    @NotBlank
    private String dateOfBirth;
    @NotBlank
    @Regex(pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password must contain alpha-numeric characters with no white-space")
    private String password;
    @NotBlank
    @Regex(pattern = "^[a-zA-Z0-9]{8,}$", message = "Username must contain alpha-numeric characters with no white-space")
    private String username;
    @NotBlank
    @Regex(pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email format does not match!")
    private String email;
}
