package com.devkind.barebonesystem.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseRestDto {
    private String message;
    private Object data;
}
