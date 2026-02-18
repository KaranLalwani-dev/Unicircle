package com.teamdev.group_up.dto;

import java.time.Instant;
import java.util.List;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateGroupRequest(
    @NotBlank(message = "Group name is required")
    @Size(min = 10, max = 200, message = "Title must be between 10 and 200 characters")
    String title,

    @NotBlank(message = "Description is required")
    @Size(min = 20, max = 2000, message = "Description must be between 20 and 2000 characters")
    String description,

    @NotNull(message = "Activity date and time is required")
    @Future(message = "Activity date and time must be in the future")
    Instant activityDateTime,

    @NotNull(message = "Maximum members is required")
    @Min(value = 2, message = "At least 2 members are required")
    @Max(value = 20, message = "Maximum 20 members allowed")
    Integer maxMembers,

    @NotBlank(message = "At least one tag is required")
    @Size(min = 1, max = 5, message = "Select between 1 and 5 tags")
    List<Long> tagIds
) {
 
    
}
