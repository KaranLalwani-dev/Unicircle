package com.teamdev.group_up.repository;

import com.teamdev.group_up.entity.JoinRequest;
import com.teamdev.group_up.enums.RequestStatus;
import com.teamdev.group_up.repository.projection.GroupIdOnly;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    boolean existsByGroup_GroupIdAndUser_UserIdAndStatus(Long groupId, Long userId, RequestStatus status);
    int countByGroup_GroupIdAndStatus(Long groupId, RequestStatus status);
    Page<JoinRequest> findByUser_UserId(Long userId, Pageable pageable);
    Page<JoinRequest> findByUser_UserIdAndStatus(Long userId, RequestStatus status, Pageable pageable);
    Page<JoinRequest> findByGroup_Creator_UserId(Long creatorId, Pageable pageable);
    Page<JoinRequest> findByGroup_Creator_UserIdAndStatus(Long creatorId, RequestStatus status, Pageable pageable);
    @Query("SELECT jr.group.groupId AS groupId FROM JoinRequest jr " +
            "WHERE jr.group.groupId IN :groupIds AND jr.user.userId = :userId AND jr.status = :status")
    List<GroupIdOnly> findPendingRequestGroupIds(@Param("groupIds") List<Long> groupIds,
                                                 @Param("userId") Long userId,
                                                 @Param("status") RequestStatus status);
}