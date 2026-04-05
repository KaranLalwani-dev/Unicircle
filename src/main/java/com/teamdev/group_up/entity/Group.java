package com.teamdev.group_up.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.teamdev.group_up.enums.GroupStatus;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long groupId;

    @ManyToOne
    @JoinColumn(name = "creatorId", nullable = false)
    User creator;

    @Column(nullable = false, length = 200)
    String title;

    @Column(nullable = false, length = 4000)
    String description;

    @Column(nullable = false)
    Instant activityDateTime;

    @Column(nullable = false)
    Integer maxMembers;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Default
    GroupStatus status = GroupStatus.OPEN;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Instant createdAt;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<GroupTag> groupTags = new ArrayList<>();
}