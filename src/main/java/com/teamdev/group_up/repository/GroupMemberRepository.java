package com.teamdev.group_up.repository;

import com.teamdev.group_up.entity.GroupMember;
import com.teamdev.group_up.entity.GroupMemberId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
    List<GroupMember> findById_GroupId(Long groupId);
    Page<GroupMember> findById_UserId(Long userId, Pageable pageable);
    boolean existsById_GroupIdAndId_UserId(Long groupId, Long userId);
    int countById_GroupId(Long groupId);

}
