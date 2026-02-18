package com.teamdev.group_up.mapper;

import org.mapstruct.Mapper;

import com.teamdev.group_up.dto.auth.SignupRequest;
import com.teamdev.group_up.dto.auth.UserProfileResponse;
import com.teamdev.group_up.entity.User;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "name", target = "name")
    User toUserEntityFromSignupRequest(SignupRequest signupRequest);
    UserProfileResponse toUserProfileResponse(User user);
}
