package com.teamdev.group_up.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "group_tags")
public class GroupTag {

    @EmbeddedId
    GroupTagId id;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    Group group;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    Tag tag;
}