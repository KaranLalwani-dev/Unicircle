package com.teamdev.group_up.service.Impl;

import com.teamdev.group_up.dto.*;
import com.teamdev.group_up.dto.auth.UserProfileResponse;
import com.teamdev.group_up.entity.*;
import com.teamdev.group_up.enums.GroupStatus;
import com.teamdev.group_up.enums.RequestStatus;
import com.teamdev.group_up.error.BadRequestException;
import com.teamdev.group_up.error.ResourceNotFoundException;
import com.teamdev.group_up.mapper.GroupMapper;
import com.teamdev.group_up.mapper.UserMapper;
import com.teamdev.group_up.repository.*;
import com.teamdev.group_up.security.AuthUtil;
import com.teamdev.group_up.service.GroupService;
import com.teamdev.group_up.specification.GroupSpecification;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GroupServiceImpl implements GroupService {

    GroupRepository groupRepository;
    GroupMemberRepository groupMemberRepository;
    JoinRequestRepository joinRequestRepository;
    TagRepository tagRepository;
    UserRepository userRepository;
    GroupMapper groupMapper;
    UserMapper userMapper;
    AuthUtil authUtil;

    @Override
    @Transactional
    public GroupDetailResponse createGroup(CreateGroupRequest request) {
        Long currentUserId = authUtil.getCurrentUserId();
        User creator = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", currentUserId.toString()));

        Group group = Group.builder()
                .creator(creator)
                .title(request.title())
                .description(request.description())
                .activityDateTime(request.activityDateTime())
                .maxMembers(request.maxMembers())
                .status(GroupStatus.OPEN)
                .build();

        group = groupRepository.save(group);

        // save tags
        Group finalGroup = group;
        List<GroupTag> groupTags = request.tagIds().stream()
                .map(tagId -> {
                    Tag tag = tagRepository.findById(tagId)
                            .orElseThrow(() -> new ResourceNotFoundException("Tag", tagId.toString()));
                    return GroupTag.builder()
                            .id(new GroupTagId(finalGroup.getGroupId(), tagId))
                            .group(finalGroup)
                            .tag(tag)
                            .build();
                }).collect(Collectors.toCollection(ArrayList::new));

        group.getGroupTags().addAll(groupTags);
        group = groupRepository.save(group);

        // add creator as first member
        GroupMember creatorMember = GroupMember.builder()
                .id(new GroupMemberId(group.getGroupId(), currentUserId))
                .group(group)
                .user(creator)
                .build();
        groupMemberRepository.save(creatorMember);

        return buildGroupDetailResponse(group, currentUserId);
    }

    @Override
    @Transactional
    public Page<GroupSummaryResponse> searchGroups(SearchGroupRequest request, PageRequest pageRequest) {
        Long currentUserId = authUtil.getCurrentUserId();
        Specification<Group> spec = GroupSpecification.withFilters(request);
        return groupRepository.findAll(spec, pageRequest)
                .map(group -> buildGroupSummaryResponse(group, currentUserId));
    }

    @Override
    @Transactional
    public GroupDetailResponse getGroupDetailsById(Long groupId) {
        Long currentUserId = authUtil.getCurrentUserId();
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", groupId.toString()));
        return buildGroupDetailResponse(group, currentUserId);
    }

    @Override
    @Transactional
    public Page<GroupSummaryResponse> getUserGroups(PageRequest pageRequest) {
        Long currentUserId = authUtil.getCurrentUserId();
        return groupRepository.findByCreator_UserId(currentUserId, pageRequest)
                .map(group -> buildGroupSummaryResponse(group, currentUserId));
    }

    @Override
    @Transactional
    public Page<GroupSummaryResponse> getGroupsJoinedByUser(PageRequest pageRequest) {
        Long currentUserId = authUtil.getCurrentUserId();
        return groupMemberRepository.findById_UserId(currentUserId, pageRequest)
                .map(member -> buildGroupSummaryResponse(member.getGroup(), currentUserId));
    }

    @Override
    @Transactional
    public void cancelGroup(Long groupId) {
        Long currentUserId = authUtil.getCurrentUserId();
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", groupId.toString()));

        if (!group.getCreator().getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("Only the group creator can cancel this group");
        }

        if (group.getStatus() == GroupStatus.CANCELLED) {
            throw new BadRequestException("Group is already cancelled");
        }

        group.setStatus(GroupStatus.CANCELLED);
        groupRepository.save(group);
    }

    @Override
    @Transactional
    public JoinRequestResponse requestToJoinGroup(Long groupId) {
        Long currentUserId = authUtil.getCurrentUserId();
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", groupId.toString()));

        if (group.getCreator().getUserId().equals(currentUserId)) {
            throw new BadRequestException("You cannot join your own group");
        }

        if (group.getStatus() != GroupStatus.OPEN) {
            throw new BadRequestException("Group is not open for joining");
        }

        boolean isAlreadyMember = groupMemberRepository
                .existsById_GroupIdAndId_UserId(groupId, currentUserId);
        if (isAlreadyMember) {
            throw new BadRequestException("You are already a member of this group");
        }

        boolean hasPendingRequest = joinRequestRepository
                .existsByGroup_GroupIdAndUser_UserIdAndStatus(groupId, currentUserId, RequestStatus.PENDING);
        if (hasPendingRequest) {
            throw new BadRequestException("You already have a pending request for this group");
        }

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", currentUserId.toString()));

        JoinRequest joinRequest = JoinRequest.builder()
                .group(group)
                .user(user)
                .status(RequestStatus.PENDING)
                .build();

        joinRequest = joinRequestRepository.save(joinRequest);
        return buildJoinRequestResponse(joinRequest);
    }

    @Override
    @Transactional
    public void leaveGroup(Long groupId) {
        Long currentUserId = authUtil.getCurrentUserId();
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", groupId.toString()));

        if (group.getCreator().getUserId().equals(currentUserId)) {
            throw new BadRequestException("Group creator cannot leave the group, cancel it instead");
        }

        GroupMemberId memberId = new GroupMemberId(groupId, currentUserId);
        if (!groupMemberRepository.existsById(memberId)) {
            throw new BadRequestException("You are not a member of this group");
        }

        groupMemberRepository.deleteById(memberId);

        // reopen group if it was full
        if (group.getStatus() == GroupStatus.FULL) {
            group.setStatus(GroupStatus.OPEN);
            groupRepository.save(group);
        }
    }

    @Override
    public List<UserProfileResponse> getGroupMembers(Long groupId) {
        groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", groupId.toString()));
        return groupMemberRepository.findById_GroupId(groupId)
                .stream()
                .map(member -> userMapper.toUserProfileResponse(member.getUser()))
                .toList();
    }

    // helpers
    private GroupDetailResponse buildGroupDetailResponse(Group group, Long currentUserId) {
        List<TagResponse> tags = group.getGroupTags().stream()
                .map(gt -> new TagResponse(gt.getTag().getTagId(), gt.getTag().getTagName()))
                .toList();

        List<UserProfileResponse> members = groupMemberRepository
                .findById_GroupId(group.getGroupId())
                .stream()
                .map(m -> userMapper.toUserProfileResponse(m.getUser()))
                .toList();

        boolean isCreator = group.getCreator().getUserId().equals(currentUserId);
        boolean isMember = groupMemberRepository.existsById_GroupIdAndId_UserId(group.getGroupId(), currentUserId);
        boolean hasPendingRequest = joinRequestRepository
                .existsByGroup_GroupIdAndUser_UserIdAndStatus(group.getGroupId(), currentUserId, RequestStatus.PENDING);

        return new GroupDetailResponse(
                group.getGroupId(),
                group.getTitle(),
                group.getDescription(),
                group.getActivityDateTime(),
                group.getMaxMembers(),
                members.size(),
                group.getStatus(),
                tags,
                userMapper.toUserProfileResponse(group.getCreator()),
                members,
                group.getCreatedAt(),
                isCreator,
                isMember,
                hasPendingRequest

        );
    }

    private GroupSummaryResponse buildGroupSummaryResponse(Group group, Long currentUserId) {
        List<TagResponse> tags = group.getGroupTags().stream()
                .map(gt -> new TagResponse(gt.getTag().getTagId(), gt.getTag().getTagName()))
                .toList();

        int currentMembers = groupMemberRepository.countById_GroupId(group.getGroupId());
        boolean isCreator = group.getCreator().getUserId().equals(currentUserId);
        boolean isMember = groupMemberRepository.existsById_GroupIdAndId_UserId(group.getGroupId(), currentUserId);
        boolean hasPendingRequest = joinRequestRepository
                .existsByGroup_GroupIdAndUser_UserIdAndStatus(group.getGroupId(), currentUserId, RequestStatus.PENDING);

        return new GroupSummaryResponse(
                group.getGroupId(),
                group.getTitle(),
                group.getDescription(),
                group.getActivityDateTime(),
                group.getMaxMembers(),
                currentMembers,
                group.getStatus(),
                tags,
                userMapper.toUserProfileResponse(group.getCreator()),
                group.getCreatedAt(),
                isCreator,
                isMember,
                hasPendingRequest
        );
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
