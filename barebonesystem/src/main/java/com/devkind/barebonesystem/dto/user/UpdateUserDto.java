package com.devkind.barebonesystem.dto.user;

import com.devkind.barebonesystem.validation.DateFormat;
import com.devkind.barebonesystem.validation.Regex;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserDto {
    @NotBlank
    @Regex(pattern = "^[a-zA-Z]+(?:[-\\s][a-zA-Z]+)*$", message = "Firstname only contains alphebetical characters and hyphens")
    private String firstname;
    @NotBlank
    @Regex(pattern = "^[a-zA-Z]+(?:[-\\s][a-zA-Z]+)*$", message = "Lastname only contains alphebetical characters and hyphens")
    private String lastname;
    @NotBlank
    @Regex(pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email format does not match!")
    private String email;
    @NotBlank
    @DateFormat
    private String dateOfBirth;
//    Just for checking user
    private String username;
}
