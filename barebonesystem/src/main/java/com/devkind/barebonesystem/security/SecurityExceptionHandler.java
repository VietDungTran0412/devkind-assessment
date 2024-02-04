package com.devkind.barebonesystem.security;

import com.devkind.barebonesystem.dto.ResponseRestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.io.IOException;

@Component
public class SecurityExceptionHandler implements AccessDeniedHandler, AuthenticationEntryPoint {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        if (accessDeniedException instanceof AccessDeniedException) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResponseRestDto resp = ResponseRestDto.builder().message("You don't have permission to perform this action!").build();
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writer().writeValueAsString(resp));
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResponseRestDto resp = ResponseRestDto.builder().message(authException.getMessage()).build();
        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writer().writeValueAsString(resp));
    }
}
