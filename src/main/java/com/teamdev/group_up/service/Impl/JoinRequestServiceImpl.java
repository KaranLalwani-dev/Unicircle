package com.teamdev.group_up.service.Impl;

import com.teamdev.group_up.dto.JoinRequestResponse;
import com.teamdev.group_up.enums.RequestStatus;
import com.teamdev.group_up.service.JoinRequestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JoinRequestServiceImpl implements JoinRequestService {
    @Override
    public Page<JoinRequestResponse> getRequestsByUser(RequestStatus status, int page, int size) {
        return null;
    }

    @Override
    public Page<JoinRequestResponse> getRequestsForUserGroups(RequestStatus status, int page, int size) {
        return null;
    }

    @Override
    public void acceptJoinRequest(Long requestId) {

    }

    @Override
    public void rejectJoinRequest(Long requestId) {

    }
}
