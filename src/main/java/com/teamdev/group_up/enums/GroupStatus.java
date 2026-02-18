package com.teamdev.group_up.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GroupStatus {
    OPEN("open"),
    FULL("full"),
    COMPLETED("completed"),
    CANCELLED("cancelled");

    private final String value;
}