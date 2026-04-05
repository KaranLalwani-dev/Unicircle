package com.teamdev.group_up.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GroupMemberId implements Serializable {
    @Column(name = "group_id")
    Long groupId;
    @Column(name = "user_id")
    Long userId;
}
