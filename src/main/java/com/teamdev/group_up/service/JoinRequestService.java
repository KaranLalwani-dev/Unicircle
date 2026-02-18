package com.teamdev.group_up.service;


import com.teamdev.group_up.dto.JoinRequestResponse;
import com.teamdev.group_up.enums.RequestStatus;
import org.springframework.data.domain.Page;

public interface JoinRequestService {
    Page<JoinRequestResponse> getRequestsByUser(RequestStatus status, int page, int size);

    Page<JoinRequestResponse> getRequestsForUserGroups(RequestStatus status, int page, int size);

    void acceptJoinRequest(Long requestId);

    void rejectJoinRequest(Long requestId);
}
