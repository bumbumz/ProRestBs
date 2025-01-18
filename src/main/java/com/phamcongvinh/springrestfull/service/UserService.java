package com.phamcongvinh.springrestfull.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.phamcongvinh.springrestfull.module.User;
import com.phamcongvinh.springrestfull.module.dto.Response.UserDTO;
import com.phamcongvinh.springrestfull.repository.UseRepository;

@Service
public class UserService {
    private final UseRepository useRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UseRepository useRepository,
            PasswordEncoder passwordEncoder) {
        this.useRepository = useRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ======================================================================================
    public UserDTO mapperUserToUserDTO(User param) {
        UserDTO res = new UserDTO(
                param.getId(),
                param.getName(),
                param.getEmail(),
                param.getAge(),
                param.getGender(),
                param.getAddress(),
                param.getCreatedAt(),
                param.getUpdatedAt(),
                param.getCreatedBy(),
                param.getUpdatedBy());
        return res;
    }

    public UserDTO createUser(User param) {
        param.setPassword(passwordEncoder.encode(param.getPassword()));
        User user = this.useRepository.save(param);
        UserDTO res = this.mapperUserToUserDTO(user);
        return res;
    }

    public void updateRefreshToken(String email, String resfreshToken) {
        User user = this.checkEmail(email).get();
        user.setRefreshToken(resfreshToken);
        this.useRepository.save(user);
    }

    // =====================================================================================
    public Optional<User> checkEmail(String email) {
        return this.useRepository.findByEmail(email);
    }

    public Optional<User> checkEmailAndRefreshTone(String email, String refreshToken) {
        return this.useRepository.findByEmailAndRefreshToken(email, refreshToken);
    }

}
