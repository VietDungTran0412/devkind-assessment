package com.devkind.barebonesystem.dto.activity;

import com.devkind.barebonesystem.enums.ActivityType;
import lombok.Data;

import java.util.Date;

@Data
public class ActivityDto {
    private String createdDate;
    private String description;
    private ActivityType type;

}
