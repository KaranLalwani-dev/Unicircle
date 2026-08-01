package com.teamdev.group_up.controller;

import com.teamdev.group_up.enums.GroupStatus;
import com.teamdev.group_up.enums.RequestStatus;
import com.teamdev.group_up.enums.Year;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/api/utils")
public class UtilityController {

    @GetMapping("/years")
    public ResponseEntity<List<String>> getAllYears(){
        List<String> years = Arrays.stream(Year.values()).map(Year::getValue).toList();
        return ResponseEntity.ok(years);
    }

    @GetMapping("/group-statuses")
    public ResponseEntity<List<String>> getGroupStatuses(){
        List<String> groupStatuses = Arrays.stream(GroupStatus.values()).map(GroupStatus::getValue).toList();
        return ResponseEntity.ok(groupStatuses);
    }

    @GetMapping("/request-statuses")
    public ResponseEntity<List<String>> getRequesStatuses(){
        List<String> requestStatuses = Arrays.stream(RequestStatus.values()).map(RequestStatus::getValue).toList();
        return ResponseEntity.ok(requestStatuses);
    }
}
