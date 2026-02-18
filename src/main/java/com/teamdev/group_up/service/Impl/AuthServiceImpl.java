package com.teamdev.group_up.service.Impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.teamdev.group_up.dto.auth.AuthResponse;
import com.teamdev.group_up.dto.auth.LoginRequest;
import com.teamdev.group_up.dto.auth.SignupRequest;
import com.teamdev.group_up.entity.User;
import com.teamdev.group_up.error.BadRequestException;
import com.teamdev.group_up.mapper.UserMapper;
import com.teamdev.group_up.repository.UserRepository;
import com.teamdev.group_up.security.AuthUtil;
import com.teamdev.group_up.service.AuthService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    AuthUtil authUtil;
    AuthenticationManager authenticationManager;

    @Override
    public AuthResponse signup(SignupRequest request) {
        userRepository.findByUsername(request.username()).ifPresent(user -> {
            throw new BadRequestException("User already exists with username: "+request.username());
        });

        User user = userMapper.toUserEntityFromSignupRequest(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user = userRepository.save(user);

        String token = authUtil.generateAccessToken(user);
        return new AuthResponse(
            token,
            userMapper.toUserProfileResponse(user)
        );
    }


    @Override
    public AuthResponse login(LoginRequest request) { 
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);
        return new AuthResponse(token, userMapper.toUserProfileResponse(user));
    }


    


    
}
