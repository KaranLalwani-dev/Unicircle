package com.teamdev.group_up.dto;

import java.time.Instant;
import java.util.List;

import com.teamdev.group_up.dto.auth.UserProfileResponse;
import com.teamdev.group_up.enums.GroupStatus;

public record GroupSummaryResponse(

    Long groupId,
    String title,
    String description,
    Instant activityDateTime,
    Integer maxMembers,
    Integer currentMembers,
    GroupStatus status,
    List<TagResponse> tags,
    UserProfileResponse creator,
    Instant createdAt
) {
    
}
