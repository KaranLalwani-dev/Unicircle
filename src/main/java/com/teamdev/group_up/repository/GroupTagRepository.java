package com.teamdev.group_up.repository;

import com.teamdev.group_up.entity.GroupTag;
import com.teamdev.group_up.entity.GroupTagId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupTagRepository extends JpaRepository<GroupTag, GroupTagId> {

    @Query("SELECT gt FROM GroupTag gt JOIN FETCH gt.tag WHERE gt.group.id IN :groupIds")
    List<GroupTag> findByGroupIdInWithTag(@Param("groupIds") List<Long> groupIds);
}
