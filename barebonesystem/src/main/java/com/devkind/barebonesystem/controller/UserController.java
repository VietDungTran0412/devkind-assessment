package com.devkind.barebonesystem.controller;

import com.devkind.barebonesystem.dto.ResponseRestDto;
import com.devkind.barebonesystem.dto.user.UpdateUserDto;
import com.devkind.barebonesystem.dto.user.UserDto;
import com.devkind.barebonesystem.mapper.UserMapper;
import com.devkind.barebonesystem.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    @PreAuthorize("#updateUserDto.username == authentication.principal.username")
    @ResponseStatus(HttpStatus.OK)
    public ResponseRestDto updateUser(@Valid @RequestBody UpdateUserDto updateUserDto, HttpServletRequest request) {
        log.info("Starting update existing user with username: {}", updateUserDto.getUsername());
        UserDto user = userService.update(request, updateUserDto, UserMapper.INSTANCE::toUserDto);
        return ResponseRestDto.builder().message("Successfully updated!").data(user).build();
    }
}
