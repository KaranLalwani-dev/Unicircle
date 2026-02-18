package com.teamdev.group_up.service.Impl;

import com.teamdev.group_up.dto.TagResponse;
import com.teamdev.group_up.repository.TagRepository;
import com.teamdev.group_up.service.TagsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TagsServiceImpl implements TagsService {

    TagRepository tagRepository;

    @Override
    public List<TagResponse> getAllTags() {
        return List.of();
    }
}
