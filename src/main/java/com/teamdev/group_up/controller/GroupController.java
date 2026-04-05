package com.teamdev.group_up.controller;

import com.teamdev.group_up.dto.*;
import com.teamdev.group_up.dto.auth.UserProfileResponse;
import com.teamdev.group_up.enums.Branch;
import com.teamdev.group_up.enums.Year;
import com.teamdev.group_up.error.ApiError;
import com.teamdev.group_up.service.GroupService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/api/groups")
public class GroupController {

    GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupDetailResponse> createGroup(@Valid @RequestBody CreateGroupRequest request){
        GroupDetailResponse group = groupService.createGroup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }

    @GetMapping
    public ResponseEntity<Page<GroupSummaryResponse>> browseGroups(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) List<Long> tagIds,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant dateTo,
            @RequestParam(required = false) Year creatorYear,
            @RequestParam(required = false) Branch creatorBranch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        SearchGroupRequest searchGroupRequest = SearchGroupRequest.builder()
                .keyword(keyword)
                .tagIds(tagIds)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .creatorYear(creatorYear)
                .creatorBranch(creatorBranch)
                .build();

        Page<GroupSummaryResponse> groups = groupService.searchGroups(searchGroupRequest, PageRequest.of(page, size));
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDetailResponse> getGroupDetailsById(@PathVariable Long groupId) {
        GroupDetailResponse group = groupService.getGroupDetailsById(groupId);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/my-groups")
    public ResponseEntity<Page<GroupSummaryResponse>> getUserGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<GroupSummaryResponse> groups = groupService.getUserGroups(PageRequest.of(page, size));
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/joined")
    public ResponseEntity<Page<GroupSummaryResponse>> getGroupsJoinedByUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<GroupSummaryResponse> groups = groupService.getGroupsJoinedByUser(PageRequest.of(page, size));
        return ResponseEntity.ok(groups);
    }

    @PutMapping("/{groupId}/cancel")
    public ResponseEntity<Void> cancelGroup(@PathVariable Long groupId) {
        groupService.cancelGroup(groupId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{groupId}/join")
    public ResponseEntity<JoinRequestResponse> requestToJoinGroup(
            @PathVariable Long groupId) {
        JoinRequestResponse response = groupService.requestToJoinGroup(groupId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<UserProfileResponse>> getGroupMembers(@PathVariable Long groupId) {
        List<UserProfileResponse> members = groupService.getGroupMembers(groupId);
        return ResponseEntity.ok(members);
    }

    @DeleteMapping("/{groupId}/leave")
    public ResponseEntity<Void> leaveGroup(@PathVariable Long groupId) {
        groupService.leaveGroup(groupId);
        return ResponseEntity.noContent().build();
    }
}
