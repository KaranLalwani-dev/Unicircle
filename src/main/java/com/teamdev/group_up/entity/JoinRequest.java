package com.teamdev.group_up.entity;

import java.time.Instant;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import com.teamdev.group_up.enums.RequestStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "join_requests")
public class JoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long requestId;

    @ManyToOne
    @JoinColumn(name = "groupId", nullable = false)
    Group group;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Default
    RequestStatus status = RequestStatus.PENDING;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Instant requestedAt;

    Instant respondedAt;
}