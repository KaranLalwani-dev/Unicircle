package com.teamdev.group_up.service;

import com.teamdev.group_up.dto.auth.UserProfileResponse;

public interface UserService {
    UserProfileResponse getProfile(Long userId);
}
