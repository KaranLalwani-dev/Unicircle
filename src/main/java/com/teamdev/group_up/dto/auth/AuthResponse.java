package com.teamdev.group_up.dto.auth;

public record AuthResponse(
    String token,
    UserProfileResponse user
) {
    
}
