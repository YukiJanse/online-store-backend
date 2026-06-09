package se.jensen.yuki.productservice.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import se.jensen.yuki.productservice.security.infrastructure.CustomUserDetails;
import se.jensen.yuki.productservice.security.service.JwtService;
import se.jensen.yuki.productservice.shared.exception.UserNotFoundException;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.startsWith("/v1/products/public")
                || path.equals("/actuator/health");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.debug("Starting JWT filter for request: {}", request.getRequestURI());

        final String authHeader = request.getHeader("Authorization");

        log.debug("Authorization header = {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug("No auth header");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        log.info("JWT extracted = {}", jwt);

        if (!jwtService.validateToken(jwt)) {
            log.warn("invalid JWT token");
            throw new BadCredentialsException("Invalid JWT");
        }

        Long userId = jwtService.extractUserId(jwt);
        String role = jwtService.extractRole(jwt);

        CustomUserDetails userDetails = new CustomUserDetails(
                userId,
                null,
                null,
                role
        );

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
        log.debug("Ending JwtFilter...");
    }
}
