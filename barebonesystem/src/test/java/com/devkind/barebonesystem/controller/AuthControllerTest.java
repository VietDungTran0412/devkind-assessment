package com.devkind.barebonesystem.controller;

import com.devkind.barebonesystem.dto.auth.AuthResponseDto;
import com.devkind.barebonesystem.dto.auth.UpdatePasswordDto;
import com.devkind.barebonesystem.dto.auth.UserLogInDto;
import com.devkind.barebonesystem.dto.auth.UserRegisterDto;
import com.devkind.barebonesystem.entity.User;
import com.devkind.barebonesystem.service.ActivityService;
import com.devkind.barebonesystem.service.AuthService;
import com.devkind.barebonesystem.service.JwtService;
import com.devkind.barebonesystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper is used to convert objects to JSON

    // Mock dependencies
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private UserService userService;
    @MockBean
    private ActivityService activityService;

    @Test
    public void testLogin() throws Exception {


        // Perform POST request and assert response
        UserLogInDto userLogInDto = new UserLogInDto();
        userLogInDto.setUsername("username");
        userLogInDto.setPassword("Password123");

        // Mock authentication manager behavior
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogInDto.getUsername(), userLogInDto.getPassword())))
                .thenReturn(new UsernamePasswordAuthenticationToken(userLogInDto.getUsername(), userLogInDto.getPassword()));

        User returnedUser =new User();
        returnedUser.setUsername(userLogInDto.getUsername());
        returnedUser.setPassword(userLogInDto.getPassword());

        Mockito.when(userService.getByUsername(userLogInDto.getUsername())).thenReturn(returnedUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLogInDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Successfully authenticated!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.jwt").exists()); // Assert jwt exists
    }

    @Test
    public void testRegister_ShouldReturnResponseWithStatusCreated() throws Exception {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername("vietdung13x3");
        userRegisterDto.setPassword("Test12345");
        userRegisterDto.setFirstname("Firstname");
        userRegisterDto.setLastname("Lastname");
        userRegisterDto.setDateOfBirth("2003-03-13");
        userRegisterDto.setEmail("abc@gmail.com");
        // Mock authentication manager behavior
        Mockito.when(userService.existedByUsername(userRegisterDto.getUsername())).thenReturn(false);

        // Perform POST request and assert response
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Successfully Created!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.jwt").exists()); // Assert jwt exists
    }

    @Test
    public void testRegisterWithWrongPasswordFormat_ShouldReturnStatusBadRequest() throws Exception {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername("vietdung13x3");
        userRegisterDto.setPassword("testfailedvalidation");
        userRegisterDto.setFirstname("Firstname");
        userRegisterDto.setLastname("Lastname");
        userRegisterDto.setDateOfBirth("2003-03-13");
        userRegisterDto.setEmail("abc@gmail.com");
        // Mock authentication manager behavior
        Mockito.when(userService.existedByUsername(userRegisterDto.getUsername())).thenReturn(false);

        // Perform POST request and assert response
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @Test
    public void testUpdatePassword_ShouldReturnAuthResponseWithUsernameNotNull() throws Exception {
        UpdatePasswordDto dto = new UpdatePasswordDto();
        dto.setUsername("username");
        dto.setPassword("Password1");
        dto.setNewPassword("NewPassword1");

        User expectedUser = new User();
        expectedUser.setUsername("username");
        expectedUser.setPassword("password");

        Mockito.when(userService.getByUsername("username")).thenReturn(expectedUser);
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()))).thenReturn(Mockito.any(UsernamePasswordAuthenticationToken.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/auth/update-password")
                        .with(SecurityMockMvcRequestPostProcessors.user("username"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.username").value(dto.getUsername()));
    }

    @Test
    public void testUpdatePasswordWithWrongUSer_ShouldReturnForbiddenWithMessage() throws Exception {
        UpdatePasswordDto dto = new UpdatePasswordDto();
        dto.setUsername("username");
        dto.setPassword("Password1");
        dto.setNewPassword("NewPassword1");
        mockMvc.perform(MockMvcRequestBuilders.put("/auth/update-password")
                        .with(SecurityMockMvcRequestPostProcessors.user("differentUsername"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("You don't have permission to perform this action!"));
    }
}