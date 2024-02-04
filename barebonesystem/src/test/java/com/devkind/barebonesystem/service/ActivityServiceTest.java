package com.devkind.barebonesystem.service;

import com.devkind.barebonesystem.entity.Activity;
import com.devkind.barebonesystem.entity.User;
import com.devkind.barebonesystem.enums.ActivityType;
import com.devkind.barebonesystem.repository.ActivityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;

public class ActivityServiceTest {
    @InjectMocks
    private ActivityService activityService;

    @Mock
    private ActivityRepository activityRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstname("firstname");
        user.setLastname("lastname");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave_ShouldReturnSaveActivity() {
        String description = "Description";
        Activity activity = new Activity();
        activity.setUser(user);
        activity.setDescription(description);
        activity.setCreatedDate(new Date());
        activity.setType(ActivityType.AUTH);
        Mockito.when(activityRepository.save(Mockito.any(Activity.class))).thenReturn(activity);

        Activity res = activityService.save(description, ActivityType.AUTH, user);

        Assertions.assertEquals(res.getDescription(), description);
        Assertions.assertEquals(res.getUser().getId(), user.getId());
    }
}
