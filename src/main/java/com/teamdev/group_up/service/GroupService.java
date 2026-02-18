package com.teamdev.group_up.service;


import com.teamdev.group_up.dto.*;
import com.teamdev.group_up.dto.auth.UserProfileResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface GroupService{
    GroupDetailResponse createGroup(@Valid CreateGroupRequest request);

    Page<GroupSummaryResponse> searchGroups(SearchGroupRequest searchGroupRequest, PageRequest of);

    GroupDetailResponse getGroupDetailsById(Long groupId);

    Page<GroupSummaryResponse> getUserGroups(PageRequest of);

    Page<GroupSummaryResponse> getGroupsJoinedByUser(PageRequest of);

    void cancelGroup(Long groupId);

    JoinRequestResponse requestToJoinGroup(Long groupId, JoinGroupRequest request);

    void leaveGroup(Long groupId);

    List<UserProfileResponse> getGroupMembers(Long groupId);
}
