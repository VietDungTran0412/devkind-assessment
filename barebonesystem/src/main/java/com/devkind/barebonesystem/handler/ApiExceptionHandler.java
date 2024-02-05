package com.devkind.barebonesystem.handler;

import com.devkind.barebonesystem.dto.ResponseRestDto;
import com.devkind.barebonesystem.exception.ResourceExistedException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.module.ResolutionException;

/* Handle API Exception */
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseRestDto handleUsernameNotFound(UsernameNotFoundException ex) {
        return ResponseRestDto.builder().message("Username has not been found!").build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseRestDto handleUsernameNotFound(MethodArgumentNotValidException ex) {
        return ResponseRestDto.builder().message(ex.getBody().getTitle() + ": " + ex.getBody().getDetail() ).build();
    }

    @ExceptionHandler(ResourceExistedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseRestDto handleResourceExistedException(ResourceExistedException ex) {
        return ResponseRestDto.builder().message("Conflict: " + ex.getMessage()).build();
    }

    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseRestDto handleAuthenticationException(RuntimeException ex) {
        return ResponseRestDto.builder().message(ex.getMessage()).build();
    }

//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseRestDto handleCommonException(RuntimeException ex) {
//        return ResponseRestDto.builder().message(ex.getMessage()).build();
//    }
}
