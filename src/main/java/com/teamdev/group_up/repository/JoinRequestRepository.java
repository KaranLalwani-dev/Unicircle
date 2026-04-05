package com.teamdev.group_up.repository;

import com.teamdev.group_up.entity.JoinRequest;
import com.teamdev.group_up.enums.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    boolean existsByGroup_GroupIdAndUser_UserIdAndStatus(Long groupId, Long userId, RequestStatus status);
    int countByGroup_GroupIdAndStatus(Long groupId, RequestStatus status);
    Page<JoinRequest> findByUser_UserId(Long userId, Pageable pageable);
    Page<JoinRequest> findByUser_UserIdAndStatus(Long userId, RequestStatus status, Pageable pageable);
    Page<JoinRequest> findByGroup_Creator_UserId(Long creatorId, Pageable pageable);
    Page<JoinRequest> findByGroup_Creator_UserIdAndStatus(Long creatorId, RequestStatus status, Pageable pageable);
}