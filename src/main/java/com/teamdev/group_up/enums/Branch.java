package com.teamdev.group_up.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Branch {
    CSE("CSE", "Computer Science and Engineering"),
    AI("AI", "Artificial Intelligence"),
    DS("DS", "Data Science"),
    CYS("CYS", "Cyber Security"),
    IT("IT", "Information Technology"),
    ECE("ECE", "Electronics and Communication Engineering"),
    ME("ME", "Mechanical Engineering"),
    CE("CE", "Civil Engineering"),
    EE("EE", "Electrical Engineering");

    private final String code;
    private final String fullName;
}
