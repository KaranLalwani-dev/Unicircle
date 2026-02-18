package com.teamdev.group_up.service;

import com.teamdev.group_up.dto.auth.UpdateProfileRequest;
import com.teamdev.group_up.dto.auth.UserProfileResponse;
import jakarta.validation.Valid;

public interface UserService {
    UserProfileResponse getProfile(Long userId);

    UserProfileResponse getUserProfile();

    UserProfileResponse updateUserProfile(@Valid UpdateProfileRequest request);
}
