package com.devkind.barebonesystem.dto.auth;

import com.devkind.barebonesystem.validation.Regex;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterDto {
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
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
