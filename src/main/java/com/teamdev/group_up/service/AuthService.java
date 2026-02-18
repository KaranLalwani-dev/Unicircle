package com.teamdev.group_up.service;

import com.teamdev.group_up.dto.auth.AuthResponse;
import com.teamdev.group_up.dto.auth.LoginRequest;
import com.teamdev.group_up.dto.auth.SignupRequest;

public interface AuthService {

    AuthResponse signup(SignupRequest request);
    AuthResponse login(LoginRequest request);
    
}
