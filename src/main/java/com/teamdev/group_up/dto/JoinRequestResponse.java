package com.teamdev.group_up.dto;

import java.time.Instant;

import com.teamdev.group_up.dto.auth.UserProfileResponse;
import com.teamdev.group_up.enums.RequestStatus;

public record JoinRequestResponse(
    Long requestId,
    Long groupId,
    String groupTitle,
    UserProfileResponse requester,
    RequestStatus status,
    Instant requestedAt,
    Instant respondedAt,
    Instant activityDateTime
) {
    
}
