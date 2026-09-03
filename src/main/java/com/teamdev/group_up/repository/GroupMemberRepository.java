package com.teamdev.group_up.repository;

import com.teamdev.group_up.entity.GroupMember;
import com.teamdev.group_up.entity.GroupMemberId;
import com.teamdev.group_up.repository.projection.GroupMemberCountProjection;
import com.teamdev.group_up.repository.projection.GroupMemberIdOnly;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
    List<GroupMember> findById_GroupId(Long groupId);
    Page<GroupMember> findById_UserId(Long userId, Pageable pageable);
    boolean existsById_GroupIdAndId_UserId(Long groupId, Long userId);
    int countById_GroupId(Long groupId);

    @Query("SELECT gm.id.groupId AS groupId, COUNT(gm) AS memberCount " +
            "FROM GroupMember gm WHERE gm.id.groupId IN :groupIds GROUP BY gm.id.groupId")
    List<GroupMemberCountProjection> countByGroupIds(@Param("groupIds") List<Long> groupIds);

    @Query("SELECT gm.id.groupId AS groupId, gm.id.userId AS userId " +
            "FROM GroupMember gm WHERE gm.id.groupId IN :groupIds AND gm.id.userId = :userId")
    List<GroupMemberIdOnly> findMembershipsForUser(@Param("groupIds") List<Long> groupIds, @Param("userId") Long userId);

}
