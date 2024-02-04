package com.devkind.barebonesystem.dto.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private String firstname;
    private String lastname;
    private Long id;
    private String dateOfBirth;
    private String email;
    private String username;
}
