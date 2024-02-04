package com.devkind.barebonesystem.mapper;

import com.devkind.barebonesystem.dto.activity.ActivityDto;
import com.devkind.barebonesystem.entity.Activity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ActivityMapper {
    ActivityMapper INSTANCE = Mappers.getMapper(ActivityMapper.class);
    @Mapping(target = "createdDate", source = "createdDate", dateFormat = "dd/MM/yyyy HH:mm:ss")
    ActivityDto toActivityDto(Activity activity);
}
