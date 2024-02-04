package com.devkind.barebonesystem.service;

import com.devkind.barebonesystem.dto.user.UpdateUserDto;
import com.devkind.barebonesystem.dto.user.UserDto;
import com.devkind.barebonesystem.entity.User;
import com.devkind.barebonesystem.enums.Role;
import com.devkind.barebonesystem.mapper.UserMapper;
import com.devkind.barebonesystem.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;
import java.util.function.Function;

public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ActivityService activityService;
    @Mock
    private JwtService jwtService;
    @Mock
    private Function<User, UserDto> mapper;
    @Mock
    private UserMapper userMapper;
    private User user;
    @BeforeEach
    void setUp() {
        this.user = new User();
        this.user.setUsername("username");
        this.user.setPassword("password");
        this.user.setFirstname("John");
        this.user.setLastname("Doe");
        this.user.setEmail("john.doe@example.com");
        this.user.setRole(Role.USER);
        this.mapper = UserMapper.INSTANCE::toUserDto;
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave_shouldReturnSavedUser() {
        Mockito.when(userRepository.existsByUsername(this.user.getUsername())).thenReturn(false);
        Mockito.when(userRepository.save(this.user)).thenReturn(this.user);

        User user = userService.save(this.user);

        // Then
        Assertions.assertNotNull(user);
        Assertions.assertEquals(this.user.getUsername(), user.getUsername());
    }


    @Test
    void testExistedByUsername_shouldReturnTrue() {
        Mockito.when(userRepository.existsByUsername(this.user.getUsername())).thenReturn(true);
        Assertions.assertTrue(userService.existedByUsername(this.user.getUsername()));
    }

    @Test
    void testGetByUsername_shouldReturnUser() {
        Mockito.when(userRepository.findUserByUsername(this.user.getUsername())).thenReturn(Optional.of(this.user));
        User user = userService.getByUsername(this.user.getUsername());
        Assertions.assertNotNull(user);
    }

    @Test
    void testGetByUsername_shouldReturnNull() {
        Mockito.when(userRepository.findUserByUsername(this.user.getUsername())).thenReturn(Optional.empty());
        User user = userService.getByUsername(this.user.getUsername());
        Assertions.assertNull(user);
    }

    @Test
    void testUpdateUser_shouldNotThrow() {
        // Mocking HttpServletRequest
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer token");

        // Mocking UpdateUserDto
        UpdateUserDto dto = new UpdateUserDto();
        dto.setUsername("username");
        // Mocking JwtService
        Mockito.when(jwtService.extractUsername("token")).thenReturn("username");

        // Mocking repository
        User user = new User(); // Create a user object with the username "username"
        Mockito.when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));

        // Mocking mapper
        Mockito.when(mapper.apply(user)).thenReturn(new UserDto()); // Mocking the function to return a UserDto

        // Call the method and assert
        Assertions.assertDoesNotThrow(() -> userService.update(request, dto, mapper));
    }
    @Test
    public void testUpdate_InvalidUser() {
        // Mocking HttpServletRequest
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer token");

        // Mocking UpdateUserDto
        UpdateUserDto dto = new UpdateUserDto();
        dto.setUsername("different_username");
        // Mocking JwtService
        Mockito.when(jwtService.extractUsername("token")).thenReturn("username");

        // Call the method and assert
        Assertions.assertThrows(BadCredentialsException.class, () -> userService.update(request, dto, mapper));
    }
}
