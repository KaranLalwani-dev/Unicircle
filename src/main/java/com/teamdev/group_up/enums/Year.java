package com.teamdev.group_up.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Year {
    FIRST_YEAR("first_year"),
    SECOND_YEAR("second_year"),
    THIRD_YEAR("third_year"),
    FOURTH_YEAR("fourth_year");

    private final String value;
}
