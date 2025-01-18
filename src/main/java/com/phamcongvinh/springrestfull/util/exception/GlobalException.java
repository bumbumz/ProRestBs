package com.phamcongvinh.springrestfull.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.phamcongvinh.springrestfull.module.dto.Response.RestResponse;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<RestResponse<Object>> handleAppException(AppException e) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

     @ExceptionHandler(value = {
            UsernameNotFoundException.class,
            BadCredentialsException.class,

    })
    public ResponseEntity<RestResponse<Object>> handleExceptionToken(Exception e) {
        RestResponse<Object> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

}
