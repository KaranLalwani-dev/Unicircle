package com.teamdev.group_up.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final AuthUtil authUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("incoming request: {}", request.getRequestURI());
        log.info("auth header: {}", request.getHeader("Authorization"));

        final String requestHeaderToken = request.getHeader("Authorization");
        if(requestHeaderToken == null || !requestHeaderToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = requestHeaderToken.split("Bearer ")[1];

        try {
            JwtUserPrincipal user = authUtil.verifyAccessToken(jwtToken);
            log.info("verified user: {}", user);
            if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("authentication set successfully");  // add this
            }
        } catch (Exception e) {
            log.error("JWT verification failed: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
