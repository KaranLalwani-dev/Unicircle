package com.teamdev.group_up.entity;

import java.time.Instant;
import org.hibernate.annotations.CreationTimestamp;
import com.teamdev.group_up.enums.RequestStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "join_requests", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"groupId", "userId"})
})
public class JoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long requestId;

    @Column(nullable = false)
    Long groupId;

    @Column(nullable = false)
    Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Default
    RequestStatus status = RequestStatus.PENDING;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Instant requestedAt;

    Instant respondedAt;
}       