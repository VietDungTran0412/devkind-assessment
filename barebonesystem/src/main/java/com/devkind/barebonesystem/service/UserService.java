package com.devkind.barebonesystem.service;

import com.devkind.barebonesystem.dto.PageDto;
import com.devkind.barebonesystem.dto.activity.ActivityDto;
import com.devkind.barebonesystem.dto.user.UpdateUserDto;
import com.devkind.barebonesystem.dto.user.UserDto;
import com.devkind.barebonesystem.entity.Activity;
import com.devkind.barebonesystem.entity.User;
import com.devkind.barebonesystem.enums.ActivityType;
import com.devkind.barebonesystem.mapper.UserMapper;
import com.devkind.barebonesystem.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final ActivityService activityService;

    public UserService(UserRepository repository, JwtService jwtService, ActivityService activityService) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.activityService = activityService;
    }
    public User getByUsername(String username) {
        log.info("Starting load user by username: {}", username);
        Optional<User> userWrapper = repository.findUserByUsername(username);
        return userWrapper.orElse(null);
    }

    @Transactional(rollbackFor = Exception.class)
    public User save(User user) {
        log.info("Save user with username: {}", user.getUsername());
        return repository.save(user);
    }

    public boolean existedByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public PageDto getUserActivities(Long id, Pageable pageable, Function<Activity, ActivityDto> mapper) {
        log.info("Get user activities with user id: {}", id);
        Optional<User> userWrapper = repository.findById(id);
        Page<Activity> activityPage = activityService.findByUser(userWrapper.get(), pageable);
        // Map activity to dto
        List<ActivityDto> dtos = activityPage.getContent().stream().map(mapper).collect(Collectors.toList());
        return PageDto.builder()
                .page(activityPage.getNumber())
                .totalPage(activityPage.getTotalPages())
                .totalElements(activityPage.getTotalElements())
                .size(activityPage.getSize())
                .content(dtos).build();
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDto update(HttpServletRequest request, UpdateUserDto dto, Function<User, UserDto> mapper) {
        log.info("Update user with username: {}", dto.getUsername());
        final String jwt = request.getHeader("Authorization").substring("Bearer ".length());
        final String username = jwtService.extractUsername(jwt);
        if(!username.equals(dto.getUsername())) {
            log.error("Invalid username from the request to update user");
            throw new BadCredentialsException("Bad Credentials");
        }
        Optional<User> user = repository.findUserByUsername(username);
        User updatedUser = user.get();
        UserMapper.INSTANCE.mapUpdateUserDtoToUser(dto, updatedUser);
        repository.save(updatedUser);
        activityService.save("Updated new personal information", ActivityType.AUTH, updatedUser);
        log.info("Successfully updated existing user with username: {}", username);
        return mapper.apply(updatedUser);
    }
}
