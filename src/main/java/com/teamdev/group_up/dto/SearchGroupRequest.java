package com.teamdev.group_up.dto;

import java.time.Instant;
import java.util.List;

import com.teamdev.group_up.enums.Branch;
import com.teamdev.group_up.enums.Year;

public record SearchGroupRequest(
    String keyword,
    List<Long> tagIds,
    Instant dateFrom,
    Instant dateTo,
    Year creatorYear,
    Branch creatorBranch

) {
    
}
