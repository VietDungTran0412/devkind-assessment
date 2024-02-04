package com.devkind.barebonesystem.controller;

import com.devkind.barebonesystem.dto.ResponseRestDto;
import com.devkind.barebonesystem.dto.auth.AuthResponseDto;
import com.devkind.barebonesystem.dto.auth.UpdatePasswordDto;
import com.devkind.barebonesystem.dto.auth.UserLogInDto;
import com.devkind.barebonesystem.dto.auth.UserRegisterDto;
import com.devkind.barebonesystem.entity.User;
import com.devkind.barebonesystem.mapper.UserMapper;
import com.devkind.barebonesystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseRestDto logIn(@RequestBody UserLogInDto dto) {
        log.info("Start logging in the system with username: {}", dto.getUsername());
        AuthResponseDto authResponseDto = authService.logIn(dto, UserMapper.INSTANCE::toUserDto);
        return ResponseRestDto.builder().message("Successfully authenticated!").data(authResponseDto).build();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseRestDto register(@RequestBody @Valid UserRegisterDto user) {
        log.info("Register new user with username: {}", user.getUsername());
        AuthResponseDto authResponse = authService.register(user, UserMapper.INSTANCE::toUser);
        return ResponseRestDto.builder().message("Successfully Created!").data(authResponse).build();
    }

    @PutMapping("/update-password")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("#updatePasswordDto.username == authentication.principal.username")
    public ResponseRestDto updatePassword(@RequestBody @Valid UpdatePasswordDto updatePasswordDto) {
        log.info("Update new password for user with username: {}", updatePasswordDto.getUsername());
        AuthResponseDto authResponse = authService.updatePassword(updatePasswordDto, UserMapper.INSTANCE::toUserDto);
        return ResponseRestDto.builder().message("Successfully updated!").data(authResponse).build();
    }
}
