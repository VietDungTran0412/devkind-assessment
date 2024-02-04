package com.devkind.barebonesystem.dto.auth;

import com.devkind.barebonesystem.dto.user.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDto {
    private String jwt;
    private UserDto user;
}
