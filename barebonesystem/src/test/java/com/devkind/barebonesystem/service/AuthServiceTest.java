package com.devkind.barebonesystem.service;

import com.devkind.barebonesystem.dto.auth.AuthResponseDto;
import com.devkind.barebonesystem.dto.auth.UpdatePasswordDto;
import com.devkind.barebonesystem.dto.auth.UserLogInDto;
import com.devkind.barebonesystem.dto.auth.UserRegisterDto;
import com.devkind.barebonesystem.dto.user.UserDto;
import com.devkind.barebonesystem.entity.User;
import com.devkind.barebonesystem.enums.Role;
import com.devkind.barebonesystem.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.function.Function;

public class AuthServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager manager;
    @Mock
    private JwtService jwtService;
    @Mock
    private ActivityService activityService;
    @InjectMocks
    private AuthService authService;
    @Mock
    private Function<User, UserDto> mapper;
    private User user;
    private UserRegisterDto userRegisterDto;


    @BeforeEach
    void setUp() {
        this.userRegisterDto = new UserRegisterDto();
        this.userRegisterDto.setUsername("username");
        this.userRegisterDto.setPassword("password");
        this.userRegisterDto.setDateOfBirth("2003-03-13");
        this.userRegisterDto.setFirstname("John");
        this.userRegisterDto.setLastname("Doe");
        this.userRegisterDto.setEmail("john.doe@example.com");
        this.mapper = UserMapper.INSTANCE::toUserDto;
        this.user = new User();
        this.user.setId(1L);
        this.user.setUsername("username");
        this.user.setPassword("password");
        this.user.setFirstname("John");
        this.user.setLastname("Doe");
        this.user.setEmail("john.doe@example.com");
        this.user.setRole(Role.USER);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_shouldReturnUser() {

        Mockito.when(userService.getByUsername("john_doe")).thenReturn(null);
        Mockito.when(passwordEncoder.encode("password")).thenReturn("encoded_password");
        Mockito.when(jwtService.generateToken(Mockito.any(User.class))).thenReturn("jwt_token");
        Function<UserRegisterDto, User> mapper = UserMapper.INSTANCE::toUser;
        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(this.user);;
        // When
        AuthResponseDto responseDto = authService.register(userRegisterDto, UserMapper.INSTANCE::toUser);

        // Then
        Assertions.assertEquals(this.user.getId(), responseDto.getUser().getId());
    }

    @Test
    void testRegisterExistingUser_shouldThrowError() {
        Mockito.when(userService.existedByUsername(this.userRegisterDto.getUsername())).thenReturn(true);

        Assertions.assertThrows(
                RuntimeException.class, () -> {
                    authService.register(userRegisterDto, UserMapper.INSTANCE::toUser);
                }
        );
    }

    @Test
    void testLogInUser_shouldReturnJwtAndUser() {
        UserLogInDto dto = new UserLogInDto();
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        Mockito.when(manager.authenticate(Mockito.any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));


        Mockito.when(userService.getByUsername(dto.getUsername())).thenReturn(user);
        Mockito.when(jwtService.generateToken(user)).thenReturn("jwt");
        UserDto userDto = new UserDto();
        userDto.setUsername(dto.getUsername());
        Mockito.when(mapper.apply(user)).thenReturn(userDto);
        // Act
        AuthResponseDto authResponseDto = authService.logIn(dto, mapper);

        // Assert
        Assertions.assertNotNull(authResponseDto);
        Assertions.assertEquals("jwt", authResponseDto.getJwt());
        Assertions.assertNotNull(authResponseDto.getUser());
    }

    @Test
    void testUpdatePassword_shouldReturnUserDto() {
        UpdatePasswordDto dto = new UpdatePasswordDto();
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setNewPassword("new_password");

        Mockito.when(manager.authenticate(Mockito.any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        Mockito.when(userService.getByUsername(dto.getUsername())).thenReturn(user);
        Mockito.when(passwordEncoder.encode(dto.getNewPassword())).thenReturn("encoded_new_password");

        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        Mockito.when(mapper.apply(user)).thenReturn(userDto);

        AuthResponseDto authResponseDto = authService.updatePassword(dto, mapper);

        Assertions.assertNotNull(authResponseDto.getUser());
        Assertions.assertEquals(dto.getUsername(), userDto.getUsername());
    }
}
