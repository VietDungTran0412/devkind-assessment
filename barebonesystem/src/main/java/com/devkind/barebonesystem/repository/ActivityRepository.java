package com.devkind.barebonesystem.repository;

import com.devkind.barebonesystem.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository  extends JpaRepository<Activity, Long> {
}
