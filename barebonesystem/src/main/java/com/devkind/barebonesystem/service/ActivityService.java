package com.devkind.barebonesystem.service;

import com.devkind.barebonesystem.entity.Activity;
import com.devkind.barebonesystem.entity.User;
import com.devkind.barebonesystem.enums.ActivityType;
import com.devkind.barebonesystem.repository.ActivityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ActivityService {
    private final ActivityRepository repository;

    public ActivityService(ActivityRepository repository) {
        this.repository = repository;
    }

    @Transactional(rollbackFor = Exception.class)
    public Activity save(String description, ActivityType type, User user) {
        Activity activity = new Activity();
        activity.setDescription(description);
        activity.setType(type);
        activity.setUser(user);
        activity.setCreatedDate(new Date());
        return repository.save(activity);
    }

    public Page<Activity> findByUser(User user, Pageable pageable) {
        return repository.findAllByUserOrderByCreatedDateCreatedDateDesc(user, pageable);
    }
}
