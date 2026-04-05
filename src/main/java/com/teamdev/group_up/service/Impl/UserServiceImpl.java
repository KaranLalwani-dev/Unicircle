package com.teamdev.group_up.service.Impl;

import com.teamdev.group_up.dto.auth.UpdateProfileRequest;
import com.teamdev.group_up.entity.User;
import com.teamdev.group_up.mapper.UserMapper;
import com.teamdev.group_up.security.AuthUtil;
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
    UserMapper userMapper;
    AuthUtil authUtil;

    @Override
    public UserProfileResponse getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
        return userMapper.toUserProfileResponse(user);
    }

    @Override
    public UserProfileResponse getUserProfile() {
        Long currentUserId = authUtil.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", currentUserId.toString()));
        return userMapper.toUserProfileResponse(user);
    }

    @Override
    public UserProfileResponse updateUserProfile(@Valid UpdateProfileRequest request) {
        Long currentUserId = authUtil.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", currentUserId.toString()));

        if (request.name() != null) user.setName(request.name());
        if (request.year() != null) user.setYear(request.year());
        if (request.branch() != null) user.setBranch(request.branch());
        if (request.instagramId() != null) user.setInstagramId(request.instagramId());
        if (request.phoneNumber() != null) user.setPhoneNumber(request.phoneNumber());

        userRepository.save(user);
        return userMapper.toUserProfileResponse(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", username));
    }
}