package com.teamdev.group_up.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(

    @NotBlank(message = "Username is required")
    @Email(message = "Username must be a valid email address")
    String username,

    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 50)
    String password
    
) {
}
