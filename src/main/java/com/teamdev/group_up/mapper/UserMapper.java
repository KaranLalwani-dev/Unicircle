package com.teamdev.group_up.mapper;

import org.mapstruct.Mapper;

import com.teamdev.group_up.dto.auth.SignupRequest;
import com.teamdev.group_up.dto.auth.UserProfileResponse;
import com.teamdev.group_up.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUserEntityFromSignupRequest(SignupRequest signupRequest);
    UserProfileResponse toUserProfileResponse(User user);
}
