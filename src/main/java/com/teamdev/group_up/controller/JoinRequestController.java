package com.teamdev.group_up.controller;

import com.teamdev.group_up.dto.JoinRequestResponse;
import com.teamdev.group_up.enums.RequestStatus;
import com.teamdev.group_up.service.JoinRequestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/api/requests")
public class JoinRequestController {

    JoinRequestService joinRequestService;

    @GetMapping("/my-requests")
    public ResponseEntity<Page<JoinRequestResponse>> getRequestsByUser(
            @RequestParam(required = false) RequestStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        Page<JoinRequestResponse> requests = joinRequestService.getRequestsByUser(status, page, size);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/for-my-groups")
    public ResponseEntity<Page<JoinRequestResponse>> getRequestsForUserGroups(
            @RequestParam(required = false) RequestStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        Page<JoinRequestResponse> requests = joinRequestService.getRequestsForUserGroups(status, page, size);
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/{requestId}/accept")
    public ResponseEntity<Void> acceptJoinRequest(@PathVariable Long requestId){
        joinRequestService.acceptJoinRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{requestId}/reject")
    public ResponseEntity<Void> rejectRequest(@PathVariable Long requestId){
        joinRequestService.rejectJoinRequest(requestId);
        return ResponseEntity.ok().build();
    }
}
