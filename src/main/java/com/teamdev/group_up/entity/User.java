package com.teamdev.group_up.entity;

import org.hibernate.annotations.CreationTimestamp;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Email;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.teamdev.group_up.enums.Branch;
import com.teamdev.group_up.enums.Year;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @Column(nullable = false, length = 50)
    String name;

    @Email
    @Column(nullable = false, unique = true, length = 100)
    String username;

    @Column(nullable = false, length = 255)
    String password;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    Year year;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    Branch branch;

    @Column(length = 50)
    String instagramId;

    @Column(length = 15)
    String phoneNumber;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Instant createdAt;

    @UpdateTimestamp
    Instant updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
}