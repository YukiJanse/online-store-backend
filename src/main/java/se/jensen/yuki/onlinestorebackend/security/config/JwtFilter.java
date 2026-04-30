package se.jensen.yuki.onlinestorebackend.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import se.jensen.yuki.onlinestorebackend.security.infrastructure.CustomUserDetails;
import se.jensen.yuki.onlinestorebackend.security.service.JwtService;
import se.jensen.yuki.onlinestorebackend.shared.exception.UserNotFoundException;
import se.jensen.yuki.onlinestorebackend.user_order.user.application.UserLoadService;
import se.jensen.yuki.onlinestorebackend.user_order.user.infrastructure.UserJpaEntity;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserLoadService userLoadService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.debug("Starting JWT filter for request: {}", request.getRequestURI());
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug("No auth header");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        if (!jwtService.validateToken(jwt)) {
            log.warn("invalid JWT token");
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = jwtService.extractUserId(jwt);

        try {
            UserJpaEntity userEntity = userLoadService.requireJpaById(userId);

            CustomUserDetails userDetails = new CustomUserDetails(
                    userEntity.getId(),
                    userEntity.getUsername(),
                    userEntity.getRole(),
                    userEntity.getPassword()
            );

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            // Set auth token
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (UserNotFoundException e) {
            log.warn("User not found for ID extracted from JWT: {}", userId);
        }

        filterChain.doFilter(request, response);
    }
}
