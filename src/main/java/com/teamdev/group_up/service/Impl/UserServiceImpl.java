package com.teamdev.group_up.service.Impl;

import com.teamdev.group_up.dto.auth.UpdateProfileRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.teamdev.group_up.dto.auth.UserProfileResponse;
import com.teamdev.group_up.error.ResourceNotFoundException;
import com.teamdev.group_up.repository.UserRepository;
import com.teamdev.group_up.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserProfileResponse getProfile(Long userId) {
        return null;
    }

    @Override
    public UserProfileResponse getUserProfile() {
        return null;
    }

    @Override
    public UserProfileResponse updateUserProfile(@Valid UpdateProfileRequest request) {
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", username));
    }
}