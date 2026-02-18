package com.teamdev.group_up.dto.auth;

import com.teamdev.group_up.enums.Branch;
import com.teamdev.group_up.enums.Year;

public record UpdateProfileRequest(
        String name,
        String email,
        Year year,
        Branch branch,
        String instagramId,
        String phoneNumber
) {
}
