package com.devkind.barebonesystem.service;

import com.devkind.barebonesystem.dto.PageDto;
import com.devkind.barebonesystem.dto.user.UpdateUserDto;
import com.devkind.barebonesystem.dto.user.UserDto;
import com.devkind.barebonesystem.entity.Activity;
import com.devkind.barebonesystem.entity.User;
import com.devkind.barebonesystem.enums.Role;
import com.devkind.barebonesystem.mapper.ActivityMapper;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    public void testGetUserActivity_shouldReturnPageDto() {
        // Create test data
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Pageable pageable = PageRequest.of(0, 10);
        List<Activity> activities = new ArrayList<>();
        activities.add(new Activity(/* construct activity object */));
        activities.add(new Activity(/* construct activity object */));
        Page<Activity> activityPage = new PageImpl<>(activities);

        // Mock repository and service behavior
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(activityService.findByUser(user, pageable)).thenReturn(activityPage);


        PageDto result = userService.getUserActivities(userId, pageable, ActivityMapper.INSTANCE::toActivityDto);

        // Verify the result
        Assertions.assertEquals(0, result.getPage()); // Verify page number
        Assertions.assertEquals(1, result.getTotalPage()); // Verify total page count
        Assertions.assertEquals(2, result.getTotalElements());
    }
}
