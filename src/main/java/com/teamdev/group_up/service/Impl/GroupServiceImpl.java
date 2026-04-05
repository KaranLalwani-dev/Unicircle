package com.teamdev.group_up.service.Impl;

import com.teamdev.group_up.dto.*;
import com.teamdev.group_up.dto.auth.UserProfileResponse;
import com.teamdev.group_up.service.GroupService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GroupServiceImpl implements GroupService {
    @Override
    public GroupDetailResponse createGroup(CreateGroupRequest request) {
        return null;
    }

    @Override
    public Page<GroupSummaryResponse> searchGroups(SearchGroupRequest searchGroupRequest, PageRequest of) {
        return null;
    }

    @Override
    public GroupDetailResponse getGroupDetailsById(Long groupId) {
        return null;
    }

    @Override
    public Page<GroupSummaryResponse> getUserGroups(PageRequest of) {
        return null;
    }

    @Override
    public Page<GroupSummaryResponse> getGroupsJoinedByUser(PageRequest of) {
        return null;
    }

    @Override
    public void cancelGroup(Long groupId) {

    }

    @Override
    public JoinRequestResponse requestToJoinGroup(Long groupId) {
        return null;
    }

    @Override
    public void leaveGroup(Long groupId) {

    }

    @Override
    public List<UserProfileResponse> getGroupMembers(Long groupId) {
        return List.of();
    }
}
