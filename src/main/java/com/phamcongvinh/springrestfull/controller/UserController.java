package com.phamcongvinh.springrestfull.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phamcongvinh.springrestfull.module.User;
import com.phamcongvinh.springrestfull.module.dto.Response.UserDTO;
import com.phamcongvinh.springrestfull.service.UserService;
import com.phamcongvinh.springrestfull.util.exception.AppException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
   

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody User param) throws AppException {
        Optional<User> checkEmail = this.userService.checkEmail(param.getEmail());
        if (checkEmail.isPresent()) {
            throw new AppException("Email đã tồn tại");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.createUser(param));

    }

}
