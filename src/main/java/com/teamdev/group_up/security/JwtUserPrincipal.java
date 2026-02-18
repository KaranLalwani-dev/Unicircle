
package com.teamdev.group_up.security;

public record JwtUserPrincipal(
        Long userId,
        String username
) {
}