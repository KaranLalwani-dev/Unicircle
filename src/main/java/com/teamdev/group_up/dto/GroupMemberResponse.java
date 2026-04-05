package com.teamdev.group_up.dto;

import com.teamdev.group_up.enums.Branch;
import com.teamdev.group_up.enums.Year;

public record GroupMemberResponse(
        Long userId,
        String name,
        Year year,
        Branch branch
) {}
