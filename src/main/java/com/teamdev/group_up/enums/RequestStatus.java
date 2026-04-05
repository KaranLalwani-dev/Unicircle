package com.teamdev.group_up.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestStatus {
    PENDING("pending"),
    ACCEPTED("accepted"),
    REJECTED("rejected");

    private final String value;
}