package com.teamdev.group_up.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TagType {
    CAB_SHARE("Cab Share"),
    STUDY_GROUP("Study Group"),
    HACKATHON("Hackathon"),
    SPORTS("Sports"),
    TRAVEL_TRIP("Travel Trip"),
    FOOD_RESTRAURANT("Food/Restaurant"),
    PROJECT_COLLABORATION("Project Collaboration"),
    EVENT_WORKSHOP("Event/Workshop"),
    GAMING("Gaming");

    private final String name;
}
