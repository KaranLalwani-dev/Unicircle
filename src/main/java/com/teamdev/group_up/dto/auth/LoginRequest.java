package com.teamdev.group_up.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(

    @NotBlank(message = "Username is required")
    @Email(message = "Username must be a valid email address")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@learner\\.manipal\\.edu$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "please login with your college email id")
    String username,

    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 50)
    String password
    
) {
}
