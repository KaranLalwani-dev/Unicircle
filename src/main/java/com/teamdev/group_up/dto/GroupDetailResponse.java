package com.teamdev.group_up.dto;

import java.time.Instant;
import java.util.List;

import com.teamdev.group_up.dto.auth.UserProfileResponse;
import com.teamdev.group_up.enums.GroupStatus;

public record GroupDetailResponse(

    Long groupId,
    String title,
    String description,
    Instant activityDateTime,
    Integer maxMembers,
    Integer currentMembers,
    GroupStatus status,  // ENUM
    List<TagResponse> tags,
    UserProfileResponse creator,
    List<UserProfileResponse> members,
    Integer pendingRequestsCount,
    Instant createdAt
) {
    
}
