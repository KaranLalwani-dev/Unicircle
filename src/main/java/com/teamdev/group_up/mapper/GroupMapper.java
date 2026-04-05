package com.teamdev.group_up.mapper;

import com.teamdev.group_up.dto.CreateGroupRequest;
import com.teamdev.group_up.dto.GroupDetailResponse;
import com.teamdev.group_up.dto.GroupSummaryResponse;
import com.teamdev.group_up.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    @Mapping(target = "groupId", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Group toGroupEntity(CreateGroupRequest request);

    @Mapping(target = "currentMembers", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "members", ignore = true)
    GroupDetailResponse toGroupDetailResponse(Group group);

    @Mapping(target = "currentMembers", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "creator", ignore = true)
    GroupSummaryResponse toGroupSummaryResponse(Group group);
}
