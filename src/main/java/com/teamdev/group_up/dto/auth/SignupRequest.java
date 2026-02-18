
package com.teamdev.group_up.dto.auth;
import com.teamdev.group_up.enums.Branch;
import com.teamdev.group_up.enums.Year;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 64, message = "Username must be between 3 and 64 characters")
    String username,

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
    String password,

    @NotBlank(message = "Year is required")
    Year year,

    @NotBlank(message = "Branch is required")
    Branch branch,

    String instagramId,
    String phoneNumber
) {
    
}
