package com.teamdev.group_up.controller;

import com.teamdev.group_up.dto.TagResponse;
import com.teamdev.group_up.service.TagsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {

    TagsService tagsService;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags(){
        List<TagResponse> tags = tagsService.getAllTags();

        return ResponseEntity.ok(tags);
    }

}
