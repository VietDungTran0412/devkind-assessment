package com.devkind.barebonesystem.controller;

import com.devkind.barebonesystem.dto.user.UpdateUserDto;
import com.devkind.barebonesystem.dto.user.UserDto;
import com.devkind.barebonesystem.entity.Activity;
import com.devkind.barebonesystem.entity.User;
import com.devkind.barebonesystem.enums.Role;
import com.devkind.barebonesystem.repository.UserRepository;
import com.devkind.barebonesystem.service.ActivityService;
import com.devkind.barebonesystem.service.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;
    @MockBean
    private HttpServletRequest servletRequest;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ActivityService activityService;

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper is used to convert objects to JSON
    @Test
    void testUpdateUser_shouldReturnOkStatusWithUserDtoWithUpdateEmail() throws Exception {
        String token = "token";
        String username = "username";
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setUsername(username);
        updateUserDto.setEmail("updatedEmail@gmail.com");
        updateUserDto.setFirstname("testFirstname");
        updateUserDto.setLastname("testLastname");
        updateUserDto.setDateOfBirth("2003-03-13");

        // Mock getting the token
        Mockito.when(servletRequest.getHeader("Authorization")).thenReturn("Bearer " + token);
        Mockito.when(jwtService.extractUsername("token")).thenReturn(username);
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setEmail("email@gmail.com");
        user.setFirstname("testFirstname");
        user.setLastname("testLastname");

        Mockito.when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));


        // Perform PUT request with valid JSON payload
        mockMvc.perform(MockMvcRequestBuilders.put("/user")
                        .with(SecurityMockMvcRequestPostProcessors.user("username"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto))
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Successfully updated!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("updatedEmail@gmail.com")); // Adjust the JSONPath as needed
    }

    @Test
    void testUpdateUserWithAgeUnder18_shouldReturnBadRequestStatusCode() throws Exception {
        String username = "username";
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setUsername(username);
        updateUserDto.setEmail("updatedEmail@gmail.com");
        updateUserDto.setFirstname("testFirstname");
        updateUserDto.setLastname("testLastname");
        updateUserDto.setDateOfBirth("2010-03-13");

        // Perform PUT request with valid JSON payload
        mockMvc.perform(MockMvcRequestBuilders.put("/user")
                        .with(SecurityMockMvcRequestPostProcessors.user("username"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto))
                        .header("Authorization", "Bearer " + "token"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testUpdateUserWithWrongUsername_shouldReturnBadRequest() throws Exception {
        String username = "username";
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setUsername("wrongUsername");
        updateUserDto.setEmail("updatedEmail@gmail.com");
        updateUserDto.setFirstname("testFirstname");
        updateUserDto.setLastname("testLastname");
        updateUserDto.setDateOfBirth("2010-03-13");

        // Perform PUT request with valid JSON payload
        mockMvc.perform(MockMvcRequestBuilders.put("/user")
                        .with(SecurityMockMvcRequestPostProcessors.user(username))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto))
                        .header("Authorization", "Bearer " + "token"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void testGetUserActivity_shouldReturnMessageWithPageResponse() throws Exception {
        Long id = 1L;
        String token = "token";
        User user = new User();
        user.setId(id);
        user.setUsername("username");
        user.setRole(Role.USER);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
        Pageable pageable = PageRequest.of(0, 2);
        List<Activity> activities = new ArrayList<>();
        activities.add(new Activity(/* construct activity object */));
        activities.add(new Activity(/* construct activity object */));
        Page<Activity> activityPage = new PageImpl<>(activities);
        Mockito.when(activityService.findByUser(user, pageable)).thenReturn(activityPage);

        // Mock getting the token
        Mockito.when(servletRequest.getHeader("Authorization")).thenReturn("Bearer " + token);
        Mockito.when(jwtService.extractUsername(token)).thenReturn("username");


        // Perform GET request with valid JSON payload
        mockMvc.perform(MockMvcRequestBuilders.get("/user/" + id.toString() + "/activity?" + "page=0&" + "size=2")
                        .with(SecurityMockMvcRequestPostProcessors.user(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.page").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").isArray());
    }

    @Test
    void testGetUserActivityWithWrongUserId_shouldReturnForbidden() throws Exception {
        Long id = 1L;
        String token = "token";
        User user = new User();
        user.setId(2L);
        user.setUsername("username");
        user.setRole(Role.USER);

        // Mock getting the token
        Mockito.when(servletRequest.getHeader("Authorization")).thenReturn("Bearer " + token);
        Mockito.when(jwtService.extractUsername(token)).thenReturn("username");


        // Perform GET request with valid JSON payload
        mockMvc.perform(MockMvcRequestBuilders.get("/user/" + id.toString() + "/activity?" + "page=0&" + "size=2")
                        .with(SecurityMockMvcRequestPostProcessors.user(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
