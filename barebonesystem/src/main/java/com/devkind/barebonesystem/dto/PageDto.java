package com.devkind.barebonesystem.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageDto {
    private Integer page;
    private Integer totalPage;
    private Long totalElements;
    private Integer size;
    private Object content;
}
