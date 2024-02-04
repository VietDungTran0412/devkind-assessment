package com.devkind.barebonesystem.dto.auth;

import com.devkind.barebonesystem.validation.Regex;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordDto {
    private String username;
    private String password;
    @NotBlank
    @Regex(pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password must contain alpha-numeric characters with no white-space")
    private String newPassword;
}
