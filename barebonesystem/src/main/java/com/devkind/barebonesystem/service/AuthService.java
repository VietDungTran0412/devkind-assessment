package com.devkind.barebonesystem.service;

import com.devkind.barebonesystem.dto.ResponseRestDto;
import com.devkind.barebonesystem.dto.auth.AuthResponseDto;
import com.devkind.barebonesystem.dto.auth.UpdatePasswordDto;
import com.devkind.barebonesystem.dto.auth.UserLogInDto;
import com.devkind.barebonesystem.dto.auth.UserRegisterDto;
import com.devkind.barebonesystem.dto.user.UserDto;
import com.devkind.barebonesystem.entity.User;
import com.devkind.barebonesystem.enums.ActivityType;
import com.devkind.barebonesystem.enums.Role;
import com.devkind.barebonesystem.exception.ResourceExistedException;
import com.devkind.barebonesystem.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Function;

@Service
@Slf4j
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ActivityService activityService;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, ActivityService activityService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.activityService = activityService;
    }

    /* Log In */
    public AuthResponseDto logIn(UserLogInDto dto, Function<User, UserDto> mapper) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getUsername(), dto.getPassword()
        ));
        User user = userService.getByUsername(dto.getUsername());
        String jwt = jwtService.generateToken(user);
        activityService.save("Log into the system", ActivityType.AUTH, user);
        return AuthResponseDto.builder().jwt(jwt).user(mapper.apply(user)).build();
    }

    public AuthResponseDto register(UserRegisterDto dto, Function<UserRegisterDto, User> mapper) {
        User user = mapper.apply(dto);
        log.info("Starting saving user with username: {}", user.getUsername());
        // Check if user has been already existed
        if(userService.existedByUsername(user.getUsername())) {
            log.error("Username has been already existed!");
            throw new ResourceExistedException("Username has already existed!");
        }

        user.setRole(Role.USER);
        final String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        String jwt = jwtService.generateToken(user);
        User savedUser = userService.save(user);
        activityService.save("Created new account", ActivityType.AUTH, user);

        // Map saved user to user dto
        Function<User, UserDto> mapUserDto = UserMapper.INSTANCE::toUserDto;
        UserDto userDto = mapUserDto.apply(savedUser);
        log.info("Generated Json Web Token: {}", jwt);
        return AuthResponseDto.builder().jwt(jwt).user(userDto).build();
    }

    @Transactional(rollbackFor = Exception.class)
    public AuthResponseDto
    updatePassword(UpdatePasswordDto dto, Function<User, UserDto> mapper) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getUsername(), dto.getPassword()
        ));
        User user = userService.getByUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userService.save(user);
        activityService.save("Updated new password", ActivityType.AUTH, user);
        UserDto userDto = mapper.apply(user);
        return AuthResponseDto.builder().user(userDto).build();
    }
}
