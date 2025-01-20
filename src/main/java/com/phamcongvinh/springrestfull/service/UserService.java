package com.phamcongvinh.springrestfull.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.phamcongvinh.springrestfull.module.User;
import com.phamcongvinh.springrestfull.module.dto.Response.UserDTO;
import com.phamcongvinh.springrestfull.module.dto.Response.Filter.FilterResponse;
import com.phamcongvinh.springrestfull.module.dto.Response.Filter.Meta;
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
    //===================================================================================================

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

    public FilterResponse filterUser( Specification<User> spec,Pageable pageable )
    {
        Page<User> pageUser= this.useRepository.findAll(spec, pageable);

        Meta meta= new Meta();
        meta.setPage(pageable.getPageNumber()+1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pageUser.getTotalPages());
        meta.setTotal(pageUser.getTotalElements());

        FilterResponse res= new FilterResponse();
        res.setMeta(meta);
        List<UserDTO> listUser= pageUser.getContent().stream()
        .map(
            item->this.mapperUserToUserDTO(item)).collect(Collectors.toList());
        res.setObject(listUser);
        return res;
        }



    // =====================================================================================
    public Optional<User> checkEmail(String email) {
        return this.useRepository.findByEmail(email);
    }
    public Optional<User> checkId(long id) {
        return this.useRepository.findById(id);
    }

    public Optional<User> checkEmailAndRefreshTone(String email, String refreshToken) {
        return this.useRepository.findByEmailAndRefreshToken(email, refreshToken);
    }

    

}
