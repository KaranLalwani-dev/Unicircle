package com.teamdev.group_up.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(nullable = false)
    Long creatorId;

    @Column(nullable = false, length = 200)
    String title;

    @Column(nullable = false, length = 4000)
    String description;

    @Column(nullable = false)
    Instant activityDateTime;

    @Column(nullable = false)
    Integer maxMembers;

    @Column(nullable = false)
    @Default
    Integer currentMembers = 1;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Default
    GroupStatus status = GroupStatus.OPEN;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Instant createdAt;
}
