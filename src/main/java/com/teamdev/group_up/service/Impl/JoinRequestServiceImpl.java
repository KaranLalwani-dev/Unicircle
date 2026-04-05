package com.teamdev.group_up.service.Impl;

import com.teamdev.group_up.dto.JoinRequestResponse;
import com.teamdev.group_up.entity.Group;
import com.teamdev.group_up.entity.GroupMember;
import com.teamdev.group_up.entity.GroupMemberId;
import com.teamdev.group_up.entity.JoinRequest;
import com.teamdev.group_up.enums.GroupStatus;
import com.teamdev.group_up.enums.RequestStatus;
import com.teamdev.group_up.error.BadRequestException;
import com.teamdev.group_up.error.ResourceNotFoundException;
import com.teamdev.group_up.mapper.UserMapper;
import com.teamdev.group_up.repository.GroupMemberRepository;
import com.teamdev.group_up.repository.GroupRepository;
import com.teamdev.group_up.repository.JoinRequestRepository;
import com.teamdev.group_up.security.AuthUtil;
import com.teamdev.group_up.service.JoinRequestService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JoinRequestServiceImpl implements JoinRequestService {

    JoinRequestRepository joinRequestRepository;
    GroupMemberRepository groupMemberRepository;
    GroupRepository groupRepository;
    UserMapper userMapper;
    AuthUtil authUtil;

    @Override
    public Page<JoinRequestResponse> getRequestsByUser(RequestStatus status, int page, int size) {
        Long currentUserId = authUtil.getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size);

        Page<JoinRequest> requests = status != null
                ? joinRequestRepository.findByUser_UserIdAndStatus(currentUserId, status, pageable)
                : joinRequestRepository.findByUser_UserId(currentUserId, pageable);

        return requests.map(this::buildJoinRequestResponse);
    }

    @Override
    public Page<JoinRequestResponse> getRequestsForUserGroups(RequestStatus status, int page, int size) {
        Long currentUserId = authUtil.getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size);

        Page<JoinRequest> requests = status != null
                ? joinRequestRepository.findByGroup_Creator_UserIdAndStatus(currentUserId, status, pageable)
                : joinRequestRepository.findByGroup_Creator_UserId(currentUserId, pageable);

        return requests.map(this::buildJoinRequestResponse);
    }

    @Override
    @Transactional
    public void acceptJoinRequest(Long requestId) {
        Long currentUserId = authUtil.getCurrentUserId();

        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("JoinRequest", requestId.toString()));

        if (!joinRequest.getGroup().getCreator().getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("Only the group creator can accept requests");
        }

        if (joinRequest.getStatus() != RequestStatus.PENDING) {
            throw new BadRequestException("Request is no longer pending");
        }

        Group group = joinRequest.getGroup();

        int currentMembers = groupMemberRepository.countById_GroupId(group.getGroupId());
        if (currentMembers >= group.getMaxMembers()) {
            throw new BadRequestException("Group is already full");
        }

        // add to group members
        GroupMember newMember = GroupMember.builder()
                .id(new GroupMemberId(group.getGroupId(), joinRequest.getUser().getUserId()))
                .group(group)
                .user(joinRequest.getUser())
                .build();
        groupMemberRepository.save(newMember);

        // update request status
        joinRequest.setStatus(RequestStatus.ACCEPTED);
        joinRequest.setRespondedAt(Instant.now());
        joinRequestRepository.save(joinRequest);

        // update group status to full if needed
        if (currentMembers + 1 >= group.getMaxMembers()) {
            group.setStatus(GroupStatus.FULL);
            groupRepository.save(group);
        }
    }

    @Override
    @Transactional
    public void rejectJoinRequest(Long requestId) {
        Long currentUserId = authUtil.getCurrentUserId();

        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("JoinRequest", requestId.toString()));

        if (!joinRequest.getGroup().getCreator().getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("Only the group creator can reject requests");
        }

        if (joinRequest.getStatus() != RequestStatus.PENDING) {
            throw new BadRequestException("Request is no longer pending");
        }

        joinRequest.setStatus(RequestStatus.REJECTED);
        joinRequest.setRespondedAt(Instant.now());
        joinRequestRepository.save(joinRequest);
    }

    private JoinRequestResponse buildJoinRequestResponse(JoinRequest joinRequest) {
        return new JoinRequestResponse(
                joinRequest.getRequestId(),
                joinRequest.getGroup().getGroupId(),
                joinRequest.getGroup().getTitle(),
                userMapper.toUserProfileResponse(joinRequest.getUser()),
                joinRequest.getStatus(),
                joinRequest.getRequestedAt(),
                joinRequest.getRespondedAt(),
                joinRequest.getGroup().getActivityDateTime()
        );
    }
}