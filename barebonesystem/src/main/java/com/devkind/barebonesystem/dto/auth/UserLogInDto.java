package com.devkind.barebonesystem.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLogInDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
