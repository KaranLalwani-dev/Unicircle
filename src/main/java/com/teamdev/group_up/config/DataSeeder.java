package com.teamdev.group_up.config;

import com.teamdev.group_up.entity.Tag;
import com.teamdev.group_up.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final TagRepository tagRepository;

    private static final List<String> DEFAULT_TAGS = List.of(
            "Cab Share", "Study Group", "Hackathon", "Hangout",
            "Sports", "Travel/Trip", "Food/Restaurant",
            "Project Collaboration", "Event/Workshop", "Gaming"
    );

    @Override
    public void run(ApplicationArguments args) {
        if (tagRepository.count() == 0) {
            DEFAULT_TAGS.forEach(name ->
                    tagRepository.save(Tag.builder().tagName(name).build())
            );
        }
    }
}