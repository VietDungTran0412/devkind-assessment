package com.devkind.barebonesystem.repository;

import com.devkind.barebonesystem.entity.Activity;
import com.devkind.barebonesystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository  extends JpaRepository<Activity, Long> {
    Page<Activity> findAllByUserOrderByCreatedDateDesc(User user, Pageable pageable);
}
